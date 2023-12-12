package com.oignonapi.infrastructure.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class TrainStationDocument(
    @Id val id: String,
    val name: String,
) {
    constructor(trainStation: TrainStation) : this(
        trainStation.id,
        trainStation.name
    )

    fun mapToDomainInstance() = TrainStation(
        id,
        name
    )
}
