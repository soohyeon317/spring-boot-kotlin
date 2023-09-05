package com.example.securityjwt.exception

enum class ErrorCode(val message: String) {

    // COMMON
    INPUT_INVALID("Input is invalid."),

    // AUTHENTICATION
    UNAUTHORIZED("Account is unauthorized."),
    RESOURCE_FORBIDDEN("Resource is forbidden."),
    ACCESS_TOKEN_EXPIRED("AccessToken is expired."),
    REFRESH_TOKEN_EXPIRED("RefreshToken is expired."),
    ACCESS_TOKEN_INVALID("AccessToken is invalid."),
    REFRESH_TOKEN_INVALID("RefreshToken is invalid."),

    // ACCOUNT
    EMAIL_INVALID("Email is invalid."),
    PASSWORD_INVALID("Password is invalid."),

    // AUTH_TOKEN
    ACCESS_TOKEN_NOT_FOUND("AccessToken is not found."),
    REFRESH_TOKEN_NOT_MATCHED("RefreshToken is not matched."),
}
