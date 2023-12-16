package com.oignonapi.infrastructure.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class TrainStationDocument(
    @Id val id: String,
    val name: String,
//    @CreatedDate
//    val createdOn: OffsetDateTime,
//    @LastModifiedDate
//    val lastUpdatedOn: OffsetDateTime
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
