package com.oignonapi.functionaltests.glue.features.trainstations

import com.oignonapi.functionaltests.glue.commons.TestContext
import com.oignonapi.presentation.trainstations.TrainDepartureResponse
import com.oignonapi.presentation.trainstations.TrainStationRequest
import com.oignonapi.presentation.trainstations.TrainStationResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TrainStationsClient(
    private val testRestTemplate: RestTemplate,
    private val testContext: TestContext,
) {
    fun uploadTrainStations(
        trainStationRequests: List<TrainStationRequest>,
    ) = testRestTemplate.exchange(
        "/train-stations",
        PUT,
        HttpEntity(trainStationRequests),
        String::class.java
    )

    fun getTrainStations(): ResponseEntity<Array<TrainStationResponse>> {
        return testRestTemplate.getForEntity(
            "/train-stations",
            Array<TrainStationResponse>::class.java
        )
    }
    fun getTrainStationUpcomingDepartures(
        trainStationId: String,
    ): ResponseEntity<Array<TrainDepartureResponse>> {
        testContext.triggerPartnerMocks()

        return testRestTemplate.getForEntity(
            "/train-stations/$trainStationId/upcoming-departures",
            Array<TrainDepartureResponse>::class.java
        )
    }
}
