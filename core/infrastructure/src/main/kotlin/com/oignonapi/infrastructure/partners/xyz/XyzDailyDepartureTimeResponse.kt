package com.oignonapi.infrastructure.partners.xyz

import com.oignonapi.domain.trainstations.TrainStationTimetable
import java.time.LocalDateTime
import java.time.ZoneId

class XyzDailyDepartureTimeResponse(
    val trainCode: String,
    val departureTime: LocalDateTime,
    val departureZoneId: ZoneId,
) {
    fun mapToTrainDeparture(): TrainStationTimetable.TrainDeparture {
        return TrainStationTimetable.TrainDeparture(
            trainCode,
            departureTime
                .atZone(departureZoneId)
                .toOffsetDateTime()
        )
    }
}
