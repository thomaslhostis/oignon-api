package com.oignonapi.functionaltests.glue.features.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.domain.trainstations.TrainStationNominals.trainStationNominal
import com.oignonapi.functionaltests.glue.commons.TestContext
import com.oignonapi.presentation.model.TrainStationResponse
import io.cucumber.java.DataTableType
import io.cucumber.java.fr.Alors
import io.cucumber.java.fr.Lorsque
import io.cucumber.java.fr.Étantdonnée
import io.cucumber.java.fr.Étantdonnées
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.springframework.http.HttpStatus.NO_CONTENT

class GererLeReferentielDeStationsDeTrains(
    private val trainStationsRecord: TrainStationsRecord,
    private val trainStationsClient: TrainStationsClient,
    private val testContext: TestContext,
) {
    private fun buildAndUploadTrainStations(shouldFail: String? = null) {
        val trainStationRequests = trainStationsRecord.buildTrainStationRequests()
        testContext.responseEntity = trainStationsClient.uploadTrainStations(trainStationRequests)
        if (shouldFail == null) {
            testContext.assertStatus(NO_CONTENT)
        }
    }

    @DataTableType
    fun trainStationEntry(trainStationEntry: Map<String, String>) =
        TrainStation(
            trainStationEntry["Identifiant"].orEmpty(),
            trainStationEntry["Nom"].orEmpty()
        )

    @Étantdonnées("les stations de trains à créer :")
    fun `les stations de trains à créer`(trainStations: List<TrainStation>) {
        trainStations.forEach(trainStationsRecord::recordTrainStation)
    }

    @Étantdonnée("une station de train existante")
    fun `une station de train existante`() {
        trainStationsRecord.recordTrainStation(trainStationNominal)
        buildAndUploadTrainStations()
    }

    @Lorsque("^j(e tente d)?'enregistrer? ces stations de trains$")
    fun `j'enregistre ces stations de trains`(shouldFail: String?) {
        buildAndUploadTrainStations(shouldFail)
    }

    @Lorsque("je récupère le référentiel des stations de trains")
    fun `je récupère le référentiel des stations de trains`() {
        testContext.responseEntity = trainStationsClient.getTrainStations()
    }

    @Alors("je reçois ces stations de trains")
    fun `je reçois ces stations de trains`() {
        testContext.assertStatusIsOk()
        val expectedTrainStationResponses = trainStationsRecord.buildTrainStationResponses()
        val actualTrainStationResponses = testContext.getResponseBodyAsListOf(TrainStationResponse::class)
        expectedTrainStationResponses shouldContainExactlyInAnyOrder actualTrainStationResponses
    }
}
