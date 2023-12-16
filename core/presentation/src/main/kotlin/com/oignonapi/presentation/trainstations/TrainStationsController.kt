package com.oignonapi.presentation.trainstations

import com.oignonapi.application.trainstations.TrainStationsUseCases
import com.oignonapi.domain.trainstations.TrainStationsPort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("train-stations")
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

    @PutMapping
    fun uploadTrainStations(
        @RequestBody trainStationRequests: List<TrainStationRequest>,
    ) {
        val trainStations = trainStationRequests.map(TrainStationRequest::mapToDomainInstance)
        trainStationsUseCases.saveTrainStations(trainStations)
    }

    @GetMapping("{trainStationId}/coming-departures")
    fun getTrainStationUpcomingDepartures(
        @PathVariable("trainStationId") trainStationId: String,
    ): List<TrainDepartureResponse> {
        return trainStationsUseCases
            .findTrainStationUpcomingDepartures(trainStationId)
            .map(::TrainDepartureResponse)
    }
}
