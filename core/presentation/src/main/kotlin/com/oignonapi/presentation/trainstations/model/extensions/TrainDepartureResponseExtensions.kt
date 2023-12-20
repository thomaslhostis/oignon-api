package com.oignonapi.presentation.trainstations.model.extensions

import com.oignonapi.domain.trainstations.TrainStationTimetable.TrainDeparture
import com.oignonapi.presentation.trainstations.model.TrainDepartureResponse

fun TrainDepartureResponse(trainDeparture: TrainDeparture) = TrainDepartureResponse(
    trainDeparture.trainNumber,
    trainDeparture.departureTime
)
