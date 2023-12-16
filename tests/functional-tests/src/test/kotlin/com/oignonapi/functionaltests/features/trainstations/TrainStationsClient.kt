package com.oignonapi.functionaltests.features.trainstations

import com.oignonapi.presentation.trainstations.TrainStationRequest
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TrainStationsClient(
    private val testRestTemplate: RestTemplate,
) {
    fun uploadTrainStations(trainStationRequests: List<TrainStationRequest>) {
        testRestTemplate.put("/train-stations", trainStationRequests)
    }
}
