package com.oignonapi.infrastructure.partners.xyz

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class XyzClient(
    private val xyzWebClient: WebClient,
) {
    fun findDailyDepartureTimes(
        trainStationId: String,
    ): List<XyzDailyDepartureTimeResponse> {
        return xyzWebClient.get()
            .uri("daily-departure-times?trainStationId=$trainStationId")
            .retrieve()
            .bodyToMono(Array<XyzDailyDepartureTimeResponse>::class.java)
            .block()
            ?.toList()
            ?: throw RuntimeException("Erreur lors de la récupération des horaires de la station $trainStationId auprès du partenaire XYZ")
    }
}
