package com.example.securityjwt.exception

class AccountNotFoundException : ResourceNotFoundException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
