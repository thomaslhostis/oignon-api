package com.oignonapi.domain.trainstations

import java.time.ZonedDateTime

class TrainStationTimetable(
    val trainStation: TrainStation,
    val dailyTrainDepartures: List<TrainDeparture>,
) {
    class TrainDeparture(
        val trainNumber: String,
        val departureTime: ZonedDateTime,
    )
}
