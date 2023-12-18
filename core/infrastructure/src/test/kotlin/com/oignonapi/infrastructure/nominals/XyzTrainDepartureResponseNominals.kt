package com.oignonapi.infrastructure.nominals

import com.oignonapi.infrastructure.partners.xyz.XyzTrainDepartureResponse
import java.time.LocalDateTime
import java.time.ZoneId

object XyzTrainDepartureResponseNominals {
    val xyzTrainDepartureResponseNominal = XyzTrainDepartureResponse(
        trainCode = "TRAIN_A",
        departureTime = LocalDateTime.now(),
        departureZoneId = ZoneId.of("Europe/Paris")
    )
}
