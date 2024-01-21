package com.oignonapi.infrastructure.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationTimetable
import com.oignonapi.domain.trainstations.TrainStationsPort
import com.oignonapi.domain.trainstations.exceptions.TrainStationNotFoundException
import com.oignonapi.infrastructure.partners.xyz.XyzClient
import com.oignonapi.infrastructure.partners.xyz.XyzTrainDepartureResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TrainStationsAdapter(
    private val trainStationsMongoRepository: TrainStationsMongoRepository,
    private val xyzRestClient: XyzClient,
) : TrainStationsPort {
    override fun saveTrainStations(trainStations: List<TrainStation>) {
        val trainStationDocuments = trainStations.map(::TrainStationDocument)
        trainStationsMongoRepository.saveAll(trainStationDocuments)
    }

    override fun findTrainStations(): List<TrainStation> {
        return trainStationsMongoRepository
            .findAll()
            .map(TrainStationDocument::mapToDomainInstance)
    }

    override fun findTrainStationTimetable(trainStationId: String): TrainStationTimetable {
        val trainStation = findTrainStation(trainStationId)
        val dailyDepartureTimes = xyzRestClient
            .getDailyDepartureTimes(trainStationId)
            .map(XyzTrainDepartureResponse::mapToTrainDeparture)

        return TrainStationTimetable(
            trainStation,
            dailyDepartureTimes
        )
    }
    private fun findTrainStation(trainStationId: String): TrainStation {
        return trainStationsMongoRepository
            .findByIdOrNull(trainStationId)
            ?.mapToDomainInstance()
            ?: throw TrainStationNotFoundException(trainStationId)
    }
}
