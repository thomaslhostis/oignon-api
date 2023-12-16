package com.oignonapi.functionaltests.features.trainstations

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
}
