package com.example.securityjwt.exception

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

const val GLOBAL_ERROR_WEB_EXCEPTION_ORDER = -2

@Order(GLOBAL_ERROR_WEB_EXCEPTION_ORDER)
@Component
class GlobalErrorWebExceptionHandler(errorAttribute: GlobalErrorAttributes,
                                     applicationContext: ApplicationContext,
                                     serverCodecConfigurer: ServerCodecConfigurer
): AbstractErrorWebExceptionHandler(errorAttribute, WebProperties.Resources(), applicationContext) {

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), this::errorResponse)
    }

    fun errorResponse(request: ServerRequest): Mono<ServerResponse> {
        val errorProperties = getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val status: Int = errorProperties["status"] as Int
        errorProperties.remove("status")
        return ServerResponse.status(HttpStatus.valueOf(status)).contentType(MediaType.APPLICATION_JSON).body(
            BodyInserters.fromValue(errorProperties)
        )
    }
}
