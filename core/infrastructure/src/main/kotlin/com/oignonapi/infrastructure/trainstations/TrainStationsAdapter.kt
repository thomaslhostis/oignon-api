package com.oignonapi.infrastructure.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationTimetable
import com.oignonapi.domain.trainstations.TrainStationsPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TrainStationsAdapter(
    private val trainStationsMongoRepository: TrainStationsMongoRepository,
) : TrainStationsPort {
    override fun saveTrainStations(trainStations: List<TrainStation>) {
        val trainStationDocuments = trainStations.map(::TrainStationDocument)
        trainStationsMongoRepository.saveAll(trainStationDocuments)
    }

    override fun findTrainStation(trainStationId: String): TrainStation {
        return trainStationsMongoRepository
            .findByIdOrNull(trainStationId)
            ?.mapToDomainInstance()
            ?: throw RuntimeException("La station de train associée à l'identifiant $trainStationId n'existe pas")
    }

    override fun findTrainStations(): List<TrainStation> {
        return trainStationsMongoRepository
            .findAll()
            .map(TrainStationDocument::mapToDomainInstance)
    }

    override fun findTrainStationTimetable(trainStationId: String): TrainStationTimetable {
        TODO("Not yet implemented")
    }
}
