package com.example.securityjwt.exception


class AuthTokenNotFoundException : ResourceNotFoundException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
