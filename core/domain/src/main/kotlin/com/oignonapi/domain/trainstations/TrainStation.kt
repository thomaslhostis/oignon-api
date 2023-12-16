package com.oignonapi.domain.trainstations

data class TrainStation(
    val id: String,
    val name: String,
) {
    companion object {
        fun hasOnlyUniqueIds(trainStations: List<TrainStation>) =
            trainStations.map(TrainStation::id)
                .toSet()
                .size == trainStations.size
    }
}
