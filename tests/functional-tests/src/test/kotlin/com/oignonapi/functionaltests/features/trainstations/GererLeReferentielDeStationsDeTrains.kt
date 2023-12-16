package com.oignonapi.functionaltests.features.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.functionaltests.configuration.TestContext
import com.oignonapi.infrastructure.trainstations.TrainStationsMongoRepository
import io.cucumber.java.DataTableType
import io.cucumber.java.fr.Alors
import io.cucumber.java.fr.Lorsque
import io.cucumber.java.fr.Étantdonnées
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder

class GererLeReferentielDeStationsDeTrains(
    private val trainStationsRecord: TrainStationsRecord,
    private val trainStationsClient: TrainStationsClient,
    private val trainStationsMongoRepository: TrainStationsMongoRepository,
    private val testContext: TestContext,
) {
    @DataTableType
    fun trainStationEntry(trainStationEntry: Map<String, String>) =
        TrainStation(
            trainStationEntry["Identifiant"].orEmpty(),
            trainStationEntry["Nom"].orEmpty()
        )

    @Étantdonnées("les stations de trains suivantes à créer :")
    fun `les stations de trains suivantes à créer`(
        trainStations: List<TrainStation>,
    ) {
        trainStations.forEach(trainStationsRecord::recordTrainStation)
    }

    @Lorsque("j'enregistre ces stations de trains")
    fun `j'enregistre ces stations de trains`() {
        val trainStationRequests = trainStationsRecord.buildTrainStationRequests()
        trainStationsClient.uploadTrainStations(trainStationRequests)
    }

    @Alors("ces stations de trains sont enregistrées avec succès")
    fun `ces stations de trains sont enregistrées avec succès`() {
        val expectedTrainStationDocuments = trainStationsRecord.buildTrainStationDocuments()
        val actualTrainStationDocuments = trainStationsMongoRepository.findAll()
        expectedTrainStationDocuments shouldContainExactlyInAnyOrder actualTrainStationDocuments
    }
}
