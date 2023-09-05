package com.example.securityjwt.exception

open class ResourceNotFoundException(code: ErrorCode, message: String?): ServiceRuntimeException(code, message)
