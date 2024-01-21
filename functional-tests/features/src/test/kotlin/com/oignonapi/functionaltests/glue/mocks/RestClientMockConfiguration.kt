package com.oignonapi.functionaltests.glue.mocks

import com.oignonapi.infrastructure.partners.xyz.XyzClient
import okhttp3.mockwebserver.MockWebServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

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
    fun restClient(mockWebServer: MockWebServer): RestClient {
        val baseUrl = mockWebServer.url("").toString()
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build()
    }

    @Bean
    fun xyzClient(restClient: RestClient) = XyzClient(restClient)
}
