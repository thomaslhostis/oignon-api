package com.oignonapi.infrastructure.partners.xyz

import com.oignonapi.domain.trainstations.TrainStationTimetable.TrainDeparture
import java.time.LocalDateTime
import java.time.ZoneId

data class XyzTrainDepartureResponse(
    val trainCode: String,
    val departureTime: LocalDateTime,
    val departureZoneId: ZoneId,
) {
    fun mapToTrainDeparture() = TrainDeparture(
        trainCode,
        departureTime
            .atZone(departureZoneId)
            .toOffsetDateTime()
    )
}
