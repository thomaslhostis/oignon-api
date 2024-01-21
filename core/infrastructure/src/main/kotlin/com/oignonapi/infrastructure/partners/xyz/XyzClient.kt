package com.oignonapi.infrastructure.partners.xyz

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class XyzClient(
    private val xyzRestClient: RestClient,
) {
    fun findDailyDepartureTimes(
        trainStationId: String,
    ): List<XyzTrainDepartureResponse> {

        return xyzRestClient.get()
            .uri("daily-departure-times?trainStationId=$trainStationId")
            .retrieve()
            .body(Array<XyzTrainDepartureResponse>::class.java)
            ?.toList()

            ?: throw RuntimeException(
                "Erreur lors de la récupération des horaires de " +
                        "la station $trainStationId auprès du partenaire XYZ"
            )
    }
}
