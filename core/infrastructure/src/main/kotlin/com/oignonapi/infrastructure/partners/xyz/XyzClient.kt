package com.oignonapi.infrastructure.partners.xyz

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange

interface XyzClient {
    @GetExchange("daily-departure-times?trainStationId={trainStationId}")
    fun getDailyDepartureTimes(
        @PathVariable trainStationId: String,
    ): List<XyzTrainDepartureResponse>
}
