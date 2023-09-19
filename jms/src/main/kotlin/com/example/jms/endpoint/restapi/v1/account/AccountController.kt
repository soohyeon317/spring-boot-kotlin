package com.example.jms.endpoint.restapi.v1.account

import com.example.jms.application.account.AccountAuthenticationCommand
import com.example.jms.application.account.AccountAuthenticationUseCase
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
                    request.email!!,
                    request.password!!)))
    }
}
