package com.oignonapi.infrastructure.partners.xyz

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class XyzRestClientConfiguration {
    @Bean
    fun xyzRestClient(): XyzClient {
        val restClient = RestClient.create()
        val restClientAdapter = RestClientAdapter.create(restClient)
        val httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build()
        return httpServiceProxyFactory.createClient(XyzClient::class.java)
    }
}
