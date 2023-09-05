package com.example.securityjwt.exception

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class GlobalErrorAttributes: DefaultErrorAttributes () {

    override fun getErrorAttributes(request: ServerRequest?, options: ErrorAttributeOptions?)
    : MutableMap<String, Any?> {

        val errorMap = mutableMapOf<String, Any?>()
        when (val throwable: Throwable = getError(request)) {
            is WebExchangeBindException -> {
                var errorContent = ErrorCode.INPUT_INVALID.message
                throwable.fieldError?.let {
                    errorContent = it.field + ":" + it.defaultMessage
                }
                errorMap["status"] = HttpStatus.BAD_REQUEST.value()
                errorMap["code"] = ErrorCode.INPUT_INVALID
                errorMap["message"] = errorContent
            }
            is UnAuthorizedException -> {
                errorMap["status"] = HttpStatus.UNAUTHORIZED.value()
                errorMap["code"] = throwable.code
                errorMap["message"] = throwable.message
            }
            is ResourceNotFoundException -> {
                errorMap["status"] = HttpStatus.NOT_FOUND.value()
                errorMap["code"] = throwable.code
                errorMap["message"] = throwable.message
            }
            is BadRequestException -> {
                errorMap["status"] = HttpStatus.BAD_REQUEST.value()
                errorMap["code"] = throwable.code
                errorMap["message"] = throwable.message
            }
            else -> {
                return super.getErrorAttributes(request, options)
            }
        }
        return errorMap
    }
}
