package com.oignonapi.presentation.trainstations

import com.oignonapi.domain.trainstations.TrainStation

class TrainStationRequest(
    val id: String,
    val name: String,
) {
    fun mapToDomainInstance() = TrainStation(
        id,
        name
    )
}
