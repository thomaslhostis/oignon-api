package com.oignonapi.infrastructure.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationNotFoundException
import com.oignonapi.domain.trainstations.TrainStationTimetable
import com.oignonapi.domain.trainstations.TrainStationsPort
import com.oignonapi.infrastructure.partners.xyz.XyzClient
import com.oignonapi.infrastructure.partners.xyz.XyzDailyDepartureTimeResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TrainStationsAdapter(
    private val trainStationsMongoRepository: TrainStationsMongoRepository,
    private val xyzClient: XyzClient,
) : TrainStationsPort {
    override fun saveTrainStations(trainStations: List<TrainStation>) {
        val trainStationDocuments = trainStations.map(::TrainStationDocument)
        trainStationsMongoRepository.saveAll(trainStationDocuments)
    }

    override fun findTrainStation(trainStationId: String): TrainStation {
        return trainStationsMongoRepository
            .findByIdOrNull(trainStationId)
            ?.mapToDomainInstance()
            ?: throw TrainStationNotFoundException(trainStationId)
    }

    override fun findTrainStations(): List<TrainStation> {
        return trainStationsMongoRepository
            .findAll()
            .map(TrainStationDocument::mapToDomainInstance)
    }

    override fun findTrainStationTimetable(trainStationId: String): TrainStationTimetable {
        val trainStation = findTrainStation(trainStationId)
        val dailyDepartureTimes = xyzClient
            .findDailyDepartureTimes(trainStationId)
            .map(XyzDailyDepartureTimeResponse::mapToTrainDeparture)

        return TrainStationTimetable(
            trainStation,
            dailyDepartureTimes
        )
    }
}
