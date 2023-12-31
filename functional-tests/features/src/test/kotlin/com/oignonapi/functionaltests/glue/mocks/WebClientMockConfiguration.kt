package com.oignonapi.functionaltests.glue.mocks

import com.oignonapi.infrastructure.partners.xyz.XyzClient
import okhttp3.mockwebserver.MockWebServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

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
class WebClientMockConfiguration {
    @Bean
    fun mockWebServer() = MockWebServer()

    @Bean
    fun webClient(mockWebServer: MockWebServer): WebClient {
        val baseUrl = mockWebServer.url("").toString()
        // Ce timeout permet d'éviter qu'un appel non bouchonné tourne dans le vide
        val timeout = ReactorClientHttpConnector(
            HttpClient.create().responseTimeout(
                Duration.ofSeconds(1)
            )
        )
        return WebClient.builder()
            .baseUrl(baseUrl)
            .clientConnector(timeout)
            .build()
    }

    @Bean
    fun xyzClient(webClient: WebClient) = XyzClient(webClient)
}
