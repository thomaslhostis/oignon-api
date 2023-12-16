package com.oignonapi.domain.trainstations

import com.oignonapi.domain.trainstations.TrainStationNominals.trainStationA
import com.oignonapi.domain.trainstations.TrainStationNominals.trainStationB
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TrainStationTest {
    @Test
    fun hasOnlyUniqueIdsTest() {

        TrainStation.hasOnlyUniqueIds(
            listOf(trainStationA)
        ) shouldBe true

        TrainStation.hasOnlyUniqueIds(
            listOf(
                trainStationA,
                trainStationB
            )
        ) shouldBe true

        TrainStation.hasOnlyUniqueIds(
            listOf(
                trainStationA,
                trainStationB,
                trainStationA,
            )
        ) shouldBe false

        TrainStation.hasOnlyUniqueIds(emptyList()) shouldBe true
    }
}
