package com.oignonapi.presentation.trainstations.model.extensions

import com.oignonapi.domain.trainstations.TrainStationTimetable
import com.oignonapi.presentation.trainstations.model.TrainDepartureResponse

fun TrainDepartureResponse(trainDeparture: TrainStationTimetable.TrainDeparture) = TrainDepartureResponse(
    trainDeparture.trainNumber,
    trainDeparture.departureTime
)
