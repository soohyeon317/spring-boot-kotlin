package com.example.securityjwt.exception

open class BadRequestException(code: ErrorCode, message: String?): ServiceRuntimeException(code, message)
