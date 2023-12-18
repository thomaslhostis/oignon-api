package com.oignonapi.functionaltests.glue.configuration

import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestTemplate
import java.io.IOException

@Configuration
class TestRestTemplateConfiguration {
    @Bean
    fun testRestTemplate(environment: Environment): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.uriTemplateHandler = LocalHostUriTemplateHandler(environment)
        restTemplate.errorHandler = NoOpResponseErrorHandler()
        return restTemplate
    }

    internal class NoOpResponseErrorHandler : DefaultResponseErrorHandler() {
        @Throws(IOException::class)
        override fun handleError(response: ClientHttpResponse) {
            // On laisse les erreurs passer parce qu'on veut les tester
        }
    }
}
