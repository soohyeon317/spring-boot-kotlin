package com.example.securityjwt.endpoint.restapi.v1.account

import com.example.securityjwt.application.account.*
import jakarta.validation.Valid
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/accounts")
@RestController
class AccountController(
    private val accountSignUpUseCase: AccountSignUpUseCase,
    private val accountSignInUseCase: AccountSignInUseCase,
    private val accountSignInRefreshUseCase: AccountSignInRefreshUseCase,
    private val accountSignOutUseCase: AccountSignOutUseCase,
    private val accountWithdrawUseCase: AccountWithdrawUseCase,
    private val messageSource: MessageSource
) {

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun signUp(
        @RequestBody @Valid request: AccountSignUpRequestDto
    ): AccountSignUpResponseDto {
        return AccountSignUpResponseDto(
            accountSignUpUseCase.signUp(
                AccountSignUpCommand.SignUp(
                    request.email!!,
                    request.password!!)))
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    suspend fun signIn(
        @RequestBody @Valid request: AccountSignInRequestDto
    ): AccountSignInResponseDto {
        val responseDto = AccountSignInResponseDto(
            accountSignInUseCase.signIn(
                AccountSignInCommand.SignIn(
                    request.email!!,
                    request.password!!)))
        println(messageSource.getMessage("sign-in.success",
            listOf(responseDto.accessToken, responseDto.refreshToken).toTypedArray(),
            request.displayLanguage!!.toLocale()))
        return responseDto
    }

    @PostMapping("/sign-in/refresh")
    @ResponseStatus(HttpStatus.OK)
    suspend fun refresh(
        @RequestBody @Valid request: AccountSignInRefreshRequestDto
    ): AccountSignInRefreshResponseDto {
        return AccountSignInRefreshResponseDto(
            accountSignInRefreshUseCase.refreshSignIn(
                AccountSignInRefreshCommand.SignInRefresh(
                    request.accessToken!!,
                    request.refreshToken!!)))
    }

    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    suspend fun signOut(
        @RequestBody @Valid request: AccountSignOutRequestDto
    ): AccountSignOutResponseDto {
        return AccountSignOutResponseDto(
            accountSignOutUseCase.signOut(
                AccountSignOutCommand.SignOut(request.accessToken!!)))
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    suspend fun withdraw(
        @RequestBody @Valid request: AccountWithdrawRequestDto
    ): AccountWithdrawResponseDto {
        return AccountWithdrawResponseDto(
            accountWithdrawUseCase.withdraw(
                AccountWithdrawCommand.Withdraw(request.password!!)))
    }
}
