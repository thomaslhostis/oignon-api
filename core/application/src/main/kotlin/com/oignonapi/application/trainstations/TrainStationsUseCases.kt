package com.oignonapi.application.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationTimetable.TrainDeparture
import com.oignonapi.domain.trainstations.TrainStationsPort
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class TrainStationsUseCases(
    private val trainStationsPort: TrainStationsPort,
) {
    fun saveTrainStations(trainStations: List<TrainStation>) {
        if (TrainStation.hasOnlyUniqueIds(trainStations)) {
            trainStationsPort.saveTrainStations(trainStations)

        } else {
            throw IllegalArgumentException(
                "L'identifiant des stations de trains doit être unique"
            )
        }
    }

    fun findTrainStationUpcomingDepartures(trainStationId: String): List<TrainDeparture> {
        return trainStationsPort
            .findTrainStationTimetable(trainStationId)
            .dailyTrainDepartures
            .filter { trainDeparture ->
                trainDeparture.departureTime.isAfter(ZonedDateTime.now())
            }
    }
}
