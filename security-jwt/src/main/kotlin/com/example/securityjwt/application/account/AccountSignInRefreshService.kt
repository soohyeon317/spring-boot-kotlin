package com.example.securityjwt.application.account

import com.example.securityjwt.configuration.authentication.AuthenticationService
import com.example.securityjwt.configuration.authentication.AuthenticationTokenType
import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import com.example.securityjwt.exception.AuthTokenNotFoundException
import com.example.securityjwt.exception.ErrorCode
import com.example.securityjwt.exception.RequestParameterInvalidException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignInRefreshService(
    private val authTokenRepository: AuthTokenRepository,
    private val authenticationService: AuthenticationService
): AccountSignInRefreshUseCase {

    @Transactional(rollbackFor=[Exception::class])
    override suspend fun refreshSignIn(command: AccountSignInRefreshCommand.SignInRefresh): AuthToken {
        // RefreshToken 유효성 검증
        authenticationService.validateToken(command.refreshToken, AuthenticationTokenType.REFRESH)

        // 저장소로부터 해당 AccessToken 정보와 일치하는 AuthToken 데이터 가져오기
        val authToken = authTokenRepository.finfindTopByAccessTokenAndDeletedAtIsNullOrderByIdDescBy(command.accessToken)
            ?: throw AuthTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)

        // RefreshToken 일치 여부 검사
        if (command.refreshToken != authToken.refreshToken) {
            throw RequestParameterInvalidException(ErrorCode.REFRESH_TOKEN_NOT_MATCHED)
        }

        val newAccessToken = authenticationService.createToken(authToken.accountId, AuthenticationTokenType.ACCESS)
        val newRefreshToken = authenticationService.createToken(authToken.accountId, AuthenticationTokenType.REFRESH)
        val updatedAuthToken = authToken.update(newAccessToken, newRefreshToken)
        return authTokenRepository.save(updatedAuthToken)
    }
}
