package com.oignonapi.functionaltests.glue.features.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.presentation.model.TrainStationResponse
import com.oignonapi.presentation.trainstations.TrainStationRequest
import org.springframework.stereotype.Component

@Component
class TrainStationsRecord(
    private var trainStations: MutableList<TrainStation>,
) {
    fun reset() {
        trainStations = mutableListOf()
    }

    fun recordTrainStation(trainStation: TrainStation) {
        trainStations.add(trainStation.copy())
    }

    fun buildTrainStationRequests() = trainStations.map { trainStation ->
        TrainStationRequest(
            trainStation.id,
            trainStation.name,
        )
    }
    fun buildTrainStationResponses() = trainStations.map { trainStation ->
        TrainStationResponse(
            trainStation.id,
            trainStation.name,
        )
    }
    fun findLastTrainStationId() = trainStations.last().id
}
