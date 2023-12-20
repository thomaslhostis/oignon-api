package com.oignonapi.functionaltests.glue.features.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.presentation.trainstations.model.TrainStationResource
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
    fun buildTrainStationRessources() = trainStations.map { trainStation ->
        TrainStationResource(
            trainStation.id,
            trainStation.name,
        )
    }

    fun findLastTrainStationId() = trainStations.last().id
}
