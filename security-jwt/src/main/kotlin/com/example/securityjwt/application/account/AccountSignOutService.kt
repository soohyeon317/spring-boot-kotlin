package com.example.securityjwt.application.account

import com.example.securityjwt.configuration.authentication.authenticationTokenManager
import com.example.securityjwt.domain.authtoken.AuthToken
import com.example.securityjwt.domain.authtoken.AuthTokenRepository
import com.example.securityjwt.exception.AuthTokenNotFoundException
import com.example.securityjwt.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignOutService(
    private val authTokenRepository: AuthTokenRepository,
    private val authenticationTokenManager: authenticationTokenManager
): AccountSignOutUseCase {

    @Transactional(rollbackFor=[Exception::class])
    // 현재 로그인 계정을 로그아웃할 수도 있고, 현재 로그인 계정과 관련된 AccessToken을 지정하여 로그아웃 시키는 것도 허용.
    override suspend fun signOut(command: AccountSignOutCommand.SignOut): AuthToken {
        val accountId = authenticationTokenManager.getAccountId()
        val selectedAccessToken = command.accessToken
        val authToken = authTokenRepository.findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId, selectedAccessToken)
            ?: throw AuthTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
        return authTokenRepository.save(authToken, true)
    }
}
