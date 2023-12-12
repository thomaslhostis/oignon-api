package com.oignonapi.presentation.trainstations

import com.oignonapi.domain.trainstations.TrainStationTimetable
import java.time.OffsetDateTime

class TrainDepartureResponse(
    val trainNumber: String,
    val departureTime: OffsetDateTime,
) {
    constructor(trainDeparture: TrainStationTimetable.TrainDeparture) : this(
        trainDeparture.trainNumber,
        trainDeparture.departureTime
    )
}
