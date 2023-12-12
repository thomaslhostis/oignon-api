package com.oignonapi.presentation.trainstations

import com.oignonapi.domain.trainstations.TrainStation

class TrainStationResponse(
    val id: String,
    val name: String,
) {
    constructor(trainStation: TrainStation) : this(
        trainStation.id,
        trainStation.name
    )
}
