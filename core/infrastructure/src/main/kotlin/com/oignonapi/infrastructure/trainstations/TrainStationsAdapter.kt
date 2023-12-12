package com.oignonapi.infrastructure.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationTimetable
import com.oignonapi.domain.trainstations.TrainStationsPort
import org.springframework.stereotype.Component

@Component
class TrainStationsAdapter : TrainStationsPort {
    override fun saveTrainStations(trainStations: List<TrainStation>) {
        TODO("Not yet implemented")
    }

    override fun findTrainStation(trainStationId: String): TrainStation {
        TODO("Not yet implemented")
    }

    override fun findTrainStations(): List<TrainStation> {
        TODO("Not yet implemented")
    }

    override fun findTrainStationTimetable(trainStationId: String): TrainStationTimetable {
        TODO("Not yet implemented")
    }
}
