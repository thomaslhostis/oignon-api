package com.oignonapi.domain.trainstations

import java.time.OffsetDateTime

class TrainStationTimetable(
    val trainStation: TrainStation,
    val dailyTrainDepartures: List<TrainDeparture>,
) {
    class TrainDeparture(
        val trainNumber: String,
        val departureTime: OffsetDateTime,
    )
}
