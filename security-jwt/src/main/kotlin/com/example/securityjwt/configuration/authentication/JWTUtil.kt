package com.example.securityjwt.configuration.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {

    @Value("\${jwt.secret-key}")
    lateinit var secretKey: String
    @Value("\${jwt.issuer}")
    lateinit var issuer: String
    @Value("\${jwt.audience}")
    lateinit var audience: String
    @Value("\${jwt.access-token-validity-in-seconds}")
    val accessTokenValidityInSeconds: Int? = null
    @Value("\${jwt.refresh-token-validity-in-seconds}")
    val refreshTokenValidityInSeconds: Int? = null

    fun parseJWTClaims(token: String): Claims = Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
        .build()
        .parseSignedClaims(token)
        .payload

    fun generate(ownerId: Long, tokenType: AuthenticationTokenType): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        if (tokenType == AuthenticationTokenType.ACCESS) {
            calendar.add(Calendar.SECOND, accessTokenValidityInSeconds!!)
        } else {
            calendar.add(Calendar.SECOND, refreshTokenValidityInSeconds!!)
        }
        val expiration = calendar.time
        return Jwts.builder()
            .issuer(issuer)
            .subject(ownerId.toString())
            .audience().add(audience)
            .and()
            .expiration(expiration)
            .issuedAt(Date())
            .id(UUID.randomUUID().toString())
            .claims(
                mapOf(
                    AuthenticationToken.OWNER_ID_CLAIM_KEY to ownerId,
                    AuthenticationToken.TOKEN_TYPE_CLAIM_KEY to tokenType
                )
            )
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .compact()
    }
}
