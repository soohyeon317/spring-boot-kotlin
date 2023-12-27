package com.example.securityjwt.configuration.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfiguration(
    private val swaggerConfigProperties: SwaggerConfigProperties
) {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("Signal Service API 문서입니다.")
            .version("v1.0.0")
        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)
        val securityRequirement = SecurityRequirement().addList("bearerAuth")
        val server = Server()
            .url(swaggerConfigProperties.serverUrl)
            .description("API Server")
        return OpenAPI()
            .components(Components().addSecuritySchemes("bearerAuth", securityScheme))
            .security(listOf(securityRequirement))
            .servers(listOf(server))
            .info(info)
    }
}
