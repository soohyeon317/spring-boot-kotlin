package com.example.securityjwt.exception

open class ServiceRuntimeException(open var code: ErrorCode, override var message: String?) : RuntimeException()
