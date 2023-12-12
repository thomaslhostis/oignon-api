package com.oignonapi.presentation.trainstations

import com.oignonapi.application.trainstations.TrainStationsUseCases
import com.oignonapi.domain.trainstations.TrainStationsPort
import org.springframework.web.bind.annotation.*

@RestController("train-stations")
class TrainStationsController(
    private val trainStationsPort: TrainStationsPort,
    private val trainStationsUseCases: TrainStationsUseCases,
) {
    @GetMapping
    fun getTrainStations(): List<TrainStationResponse> {
        return trainStationsPort
            .findTrainStations()
            .map(::TrainStationResponse)
    }

    @GetMapping("{trainStationId}")
    fun getTrainStation(
        @PathVariable("trainStationId") trainStationId: String,
    ): TrainStationResponse {
        val trainStation = trainStationsPort.findTrainStation(trainStationId)
        return TrainStationResponse(trainStation)
    }

    @PutMapping
    fun uploadTrainStations(
        @RequestBody trainStationRequests: List<TrainStationRequest>,
    ) {
        val trainStations = trainStationRequests.map(TrainStationRequest::mapToDomainInstance)
        trainStationsPort.saveTrainStations(trainStations)
    }

    @GetMapping("{trainStationId}/coming-departures")
    fun getTrainStationComingDepartures(
        @PathVariable("trainStationId") trainStationId: String,
    ): List<TrainDepartureResponse> {
        return trainStationsUseCases
            .findTrainStationComingDepartures(trainStationId)
            .map(::TrainDepartureResponse)
    }
}
