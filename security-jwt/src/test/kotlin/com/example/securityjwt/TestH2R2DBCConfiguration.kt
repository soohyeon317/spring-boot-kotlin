package com.example.securityjwt

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.transaction.annotation.EnableTransactionManagement

@Profile("test")
@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
class TestH2R2DBCConfiguration {

    @Bean
    fun connectionFactory() = H2ConnectionFactory(
        H2ConnectionConfiguration.builder().inMemory("test").property(H2ConnectionOption.DB_CLOSE_DELAY, "-1").build()
    )

    @Bean
    fun init(connectionFactory: H2ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val pop = CompositeDatabasePopulator()
        pop.addPopulators(ResourceDatabasePopulator(ClassPathResource("test-schema.sql")))
        initializer.setDatabasePopulator(pop)
        return initializer
    }
}
