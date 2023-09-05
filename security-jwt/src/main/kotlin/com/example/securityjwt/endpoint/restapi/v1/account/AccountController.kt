package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.application.account.AccountAuthenticationCommand
import com.example.securityjwt.application.account.AccountAuthenticationUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/account")
@RestController
class AccountController(
    private val accountAuthenticationUseCase: AccountAuthenticationUseCase
) {

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun signUp(
        @RequestBody @Valid request: AccountSignUpRequestDto
    ): AccountSignUpResponseDto {
        return AccountSignUpResponseDto(
            accountAuthenticationUseCase.signUp(
                AccountAuthenticationCommand.SignUp(
                    request.email,
                    request.password)))
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    suspend fun signIn(
        @RequestBody @Valid request: AccountSignInRequestDto
    ): AccountSignInResponseDto {
        return AccountSignInResponseDto(
            accountAuthenticationUseCase.signIn(
                AccountAuthenticationCommand.SignIn(
                    request.email,
                    request.password)))
    }

    @PostMapping("/sign-in/refresh")
    @ResponseStatus(HttpStatus.OK)
    suspend fun refresh(
        @RequestBody @Valid request: AccountSignInRefreshRequestDto
    ): AccountSignInResponseDto {
        return AccountSignInResponseDto(
            accountAuthenticationUseCase.refreshSignIn(
                AccountAuthenticationCommand.SignInRefresh(
                    request.accessToken,
                    request.refreshToken)))
    }

    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    suspend fun signOut(@RequestBody @Valid request: AccountSignOutRequestDto): AccountSignOutResponseDto {
        return AccountSignOutResponseDto(
            accountAuthenticationUseCase.signOut(
                AccountAuthenticationCommand.SignOut(request.accessToken)))
    }
}
