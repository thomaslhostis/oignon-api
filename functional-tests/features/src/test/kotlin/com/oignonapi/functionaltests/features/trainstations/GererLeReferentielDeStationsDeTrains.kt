package com.oignonapi.functionaltests.features.trainstations

import com.oignonapi.domain.trainstations.TrainStation
import com.oignonapi.functionaltests.configuration.TestContext
import com.oignonapi.presentation.trainstations.TrainStationResponse
import io.cucumber.java.DataTableType
import io.cucumber.java.fr.Alors
import io.cucumber.java.fr.Lorsque
import io.cucumber.java.fr.Étantdonnées
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.springframework.http.HttpStatus

class GererLeReferentielDeStationsDeTrains(
    private val trainStationsRecord: TrainStationsRecord,
    private val trainStationsClient: TrainStationsClient,
    private val testContext: TestContext,
) {
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

    @Lorsque("^j(e tente d)?'enregistrer? ces stations de trains$")
    fun `j'enregistre ces stations de trains`(shouldFail: String?) {
        val trainStationRequests = trainStationsRecord.buildTrainStationRequests()
        testContext.responseEntity = trainStationsClient.uploadTrainStations(trainStationRequests)
        if (shouldFail == null) {
            testContext.assertStatusIsOk()
        }
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

    @Alors(
        "^je reçois une erreur (\\d{3})" +
                "(?: avec le message \"([^\"]*)\")?$"
    )
    fun `je reçois une erreur`(
        expectedHttpStatusCode: Int,
        expectedErrorMessage: String?,
    ) {
        val expectedHttpStatus = HttpStatus.valueOf(expectedHttpStatusCode)
        testContext.assertStatus(expectedHttpStatus, expectedErrorMessage)
    }
}
