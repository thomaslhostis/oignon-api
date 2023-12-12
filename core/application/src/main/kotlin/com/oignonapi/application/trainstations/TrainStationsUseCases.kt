package com.oignonapi.application.trainstations

import com.oignonapi.domain.trainstations.TrainStationTimetable.TrainDeparture
import com.oignonapi.domain.trainstations.TrainStationsPort
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TrainStationsUseCases(private val trainStationsPort: TrainStationsPort) {
    fun findTrainStationComingDepartures(trainStationId: String): List<TrainDeparture> {
        return trainStationsPort
            .findTrainStationTimetable(trainStationId)
            .trainDepartures
            .filter { trainDeparture ->
                trainDeparture.departureTime.isAfter(OffsetDateTime.now())
            }
    }
}
