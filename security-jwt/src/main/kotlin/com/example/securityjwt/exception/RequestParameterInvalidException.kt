package com.example.securityjwt.exception

class RequestParameterInvalidException : BadRequestException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
