package com.oignonapi.domain.trainstations

import java.time.OffsetDateTime

class TrainStationTimetable(
    val trainStation: TrainStation,
    val trainDepartures: List<TrainDeparture>,
) {
    class TrainDeparture(
        val trainNumber: String,
        val departureTime: OffsetDateTime,
    )
}
