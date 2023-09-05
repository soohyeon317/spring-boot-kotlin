package com.example.securityjwt.configuration.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {

    companion object {
        const val SECRET_KEY: String = "u53VdWTefhj7OveEGPA0G8pbwMmDI8r84ZKkVJ7JGV5zKGvHFrgJoMBtBwPLIXtrjYo0e6vaNLl8cCAbaj097HWyANCmN8KMkrndoHOYADS0tCe8Am65pgffUEjfwdFD3yXUCu4rVstyDaIxXgHLXfriTw9srx33b0NHkr1NaGLC0T23JzXIDJzxpvTkM2zlaUaXCdO7LT9CLGuGlMEVt5uFKPT1Qd67DDmFBNTHd7UccCq1FrSKkwQ8yTQ0htq5"
        const val ISSUER: String = "issuer"
        const val AUDIENCE: String = "audience"
        const val ACCESS_TOKEN_VALIDITY_SECONDS: Int = 3600 // 1시간
        const val REFRESH_TOKEN_VALIDITY_SECONDS: Int = 2592000 // 30일
    }

    fun parseJWTClaims(token: String): Claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
        .build()
        .parseClaimsJws(token)
        .body

    fun generate(ownerId: Long, tokenType: AuthenticationTokenType): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        if (tokenType == AuthenticationTokenType.ACCESS) {
            calendar.add(Calendar.SECOND, ACCESS_TOKEN_VALIDITY_SECONDS)
        } else {
            calendar.add(Calendar.SECOND, REFRESH_TOKEN_VALIDITY_SECONDS)
        }
        val expiration = calendar.time
        return Jwts.builder()
            .setIssuer(ISSUER)
            .setSubject(ownerId.toString())
            .setAudience(AUDIENCE)
            .setExpiration(expiration)
            .setIssuedAt(Date())
            .setId(UUID.randomUUID().toString())
            .addClaims(
                mapOf(
                    AuthenticationToken.OWNER_ID_CLAIM_KEY to ownerId,
                    AuthenticationToken.TOKEN_TYPE_CLAIM_KEY to tokenType
                )
            )
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
            .compact()
    }
}
