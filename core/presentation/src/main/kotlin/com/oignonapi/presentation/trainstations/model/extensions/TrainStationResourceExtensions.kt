package com.oignonapi.presentation.trainstations.model.extensions

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.presentation.trainstations.model.TrainStationResource

fun TrainStationResource.mapToDomainInstance() = TrainStation(
    id,
    name
)

fun TrainStationResource(trainStation: TrainStation) = TrainStationResource(
    trainStation.id,
    trainStation.name
)
