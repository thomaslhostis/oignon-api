package com.oignonapi.domain.trainstations

interface TrainStationsPort {
    fun saveTrainStations(trainStations: List<TrainStation>)
    fun findTrainStation(trainStationId: String): TrainStation
    fun findTrainStations(): List<TrainStation>
    fun findTrainStationTimetable(trainStationId: String): TrainStationTimetable
}
