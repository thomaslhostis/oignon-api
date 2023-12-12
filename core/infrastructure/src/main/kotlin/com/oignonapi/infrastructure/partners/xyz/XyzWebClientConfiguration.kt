package com.oignonapi.infrastructure.partners.xyz

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class XyzWebClientConfiguration {
    @Bean
    fun xyzWebClient(): WebClient {
        return WebClient.create()
    }
}
