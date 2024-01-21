package com.oignonapi.functionaltests.glue.mocks

import com.oignonapi.infrastructure.partners.xyz.XyzClient
import okhttp3.mockwebserver.MockWebServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

/**
 * Cette classe utilise l'injection de dépendances Spring pour bouchonner le WebClient
 * permettant de faire les appels partenaires dans la couche infrastructure.
 *
 * Les bouchons sont définis à l'aide des classes `PartnerMock` et
 * `PartnerMocksRecord` et sont déclenchés juste avant l'appel à
 * l'API MBG via :
 *
 *     mockWebServer.dispatcher = partnerMocksRecord.buildDispatcher()
 */
@Configuration
class RestClientMockConfiguration {
    @Bean
    fun mockWebServer() = MockWebServer()

    @Bean
    @Primary
    fun xyzRestClient(mockWebServer: MockWebServer): XyzClient {
        val baseUrl = mockWebServer.url("").toString()
        val restClient = RestClient.builder().baseUrl(baseUrl).build()
        val restClientAdapter = RestClientAdapter.create(restClient)
        val httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build()
        return httpServiceProxyFactory.createClient(XyzClient::class.java)
    }
}
