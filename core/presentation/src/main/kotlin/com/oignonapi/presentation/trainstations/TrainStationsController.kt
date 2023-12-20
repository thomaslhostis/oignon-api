package com.oignonapi.presentation.trainstations

import com.oignonapi.application.trainstations.TrainStationsUseCases
import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationsPort
import com.oignonapi.presentation.api.TrainStationsApi
import com.oignonapi.presentation.model.TrainStationResponse
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("train-stations")
class TrainStationsController(
    private val trainStationsPort: TrainStationsPort,
    private val trainStationsUseCases: TrainStationsUseCases,
) : TrainStationsApi {
    override fun getTrainStations(): ResponseEntity<List<TrainStationResponse>> {
        // https://stackoverflow.com/questions/61476389/change-return-types-for-spring-openapi-generator-maven-plugin-generated-interfac
        val trainStationResponses = trainStationsPort
            .findTrainStations()
            .map(::fromTrainStation)

        return ResponseEntity.ok(trainStationResponses)
    }

    fun fromTrainStation(trainStation: TrainStation) = TrainStationResponse(
        trainStation.id,
        trainStation.name
    )

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    fun uploadTrainStations(
        @RequestBody trainStationRequests: List<TrainStationRequest>,
    ) {
        val trainStations = trainStationRequests.map(TrainStationRequest::mapToDomainInstance)
        trainStationsUseCases.saveTrainStations(trainStations)
    }

    @GetMapping("{trainStationId}/upcoming-departures")
    fun getTrainStationUpcomingDepartures(
        @PathVariable("trainStationId") trainStationId: String,
    ): List<TrainDepartureResponse> {
        return trainStationsUseCases
            .findTrainStationUpcomingDepartures(trainStationId)
            .map(::TrainDepartureResponse)
    }
}
