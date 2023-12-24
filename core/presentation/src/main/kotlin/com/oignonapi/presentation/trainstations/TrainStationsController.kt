package com.oignonapi.presentation.trainstations

import com.oignonapi.application.trainstations.TrainStationsUseCases
import com.oignonapi.domain.trainstations.TrainStationsPort
import com.oignonapi.presentation.trainstations.model.TrainDepartureResponse
import com.oignonapi.presentation.trainstations.model.TrainStationResource
import com.oignonapi.presentation.trainstations.model.extensions.TrainDepartureResponse
import com.oignonapi.presentation.trainstations.model.extensions.TrainStationResource
import com.oignonapi.presentation.trainstations.model.extensions.mapToDomainInstance
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class TrainStationsController(
    private val trainStationsPort: TrainStationsPort,
    private val trainStationsUseCases: TrainStationsUseCases,
) : TrainStationsApi {

    override fun getTrainStations(): ResponseEntity<List<TrainStationResource>> {
        val trainStationResponses = trainStationsPort
            .findTrainStations()
            .map(::TrainStationResource)

        return ResponseEntity.ok(trainStationResponses)
    }

    override fun uploadTrainStations(
        trainStationResources: List<TrainStationResource>,
    ): ResponseEntity<Unit> {
        val trainStations = trainStationResources
            .map(TrainStationResource::mapToDomainInstance)

        trainStationsUseCases.saveTrainStations(trainStations)

        return ResponseEntity.noContent().build()
    }

    override fun getTrainStationUpcomingDepartures(
        trainStationId: String,
    ): ResponseEntity<List<TrainDepartureResponse>> {
        val trainStationUpcomingDepartures = trainStationsUseCases
            .findTrainStationUpcomingDepartures(trainStationId)
            .map(::TrainDepartureResponse)

        return ResponseEntity.ok(trainStationUpcomingDepartures)
    }
}
