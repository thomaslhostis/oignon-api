package com.oignonapi.infrastructure.partners.xyz

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class XyzRestClientConfiguration {
    @Bean
    fun xyzRestClient(): RestClient {
        return RestClient.create()
    }
}
