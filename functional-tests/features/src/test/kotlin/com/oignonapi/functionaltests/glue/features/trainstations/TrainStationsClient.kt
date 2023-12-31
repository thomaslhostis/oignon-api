package com.oignonapi.functionaltests.glue.features.trainstations

import com.oignonapi.functionaltests.glue.commons.TestContext
import com.oignonapi.presentation.trainstations.model.TrainStationResource
import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import io.restassured.response.Response
import org.springframework.stereotype.Component

@Component
class TrainStationsClient(
    private val testContext: TestContext,
) {
    fun uploadTrainStations(
        trainStationResources: List<TrainStationResource>,
    ): Response = given()
        .body(trainStationResources)
        .put("/train-stations")

    fun getTrainStations(): Response = get("/train-stations")
    fun getTrainStationUpcomingDepartures(
        trainStationId: String,
    ): Response {
        testContext.triggerPartnerMocks()

        return get(
            "/train-stations/$trainStationId/upcoming-departures"
        )
    }
}
