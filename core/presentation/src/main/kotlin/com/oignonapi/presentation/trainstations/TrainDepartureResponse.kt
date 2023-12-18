package com.oignonapi.presentation.trainstations

import com.oignonapi.domain.trainstations.TrainStationTimetable.TrainDeparture
import java.time.OffsetDateTime

data class TrainDepartureResponse(
    val trainNumber: String,
    val departureTime: OffsetDateTime,
) {
    constructor(trainDeparture: TrainDeparture) : this(
        trainDeparture.trainNumber,
        trainDeparture.departureTime
    )
}
