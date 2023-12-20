package com.oignonapi.domain.trainstations

import com.oignonapi.domain.trainstations.TrainStationNominals.trainStationNominal
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class TrainStationTest {
    @Test
    fun hasOnlyUniqueIdsTest() {
        println("ZonedDateTime.now()=${ZonedDateTime.now()}")
        val trainStationA = trainStationNominal.copy(id = "A")
        val trainStationB = trainStationNominal.copy(id = "B")

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
