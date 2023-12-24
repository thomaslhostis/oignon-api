package com.oignonapi.functionaltests.glue.features.trainstations

import com.oignonapi.functionaltests.glue.commons.TestContext
import com.oignonapi.functionaltests.glue.mocks.PartnerMock.Factory.buildPartnerMockThatCouldFail
import com.oignonapi.functionaltests.glue.mocks.PartnerMocksRecord
import com.oignonapi.infrastructure.nominals.XyzTrainDepartureResponseNominals.xyzTrainDepartureResponseNominal
import com.oignonapi.presentation.trainstations.model.TrainDepartureResponse
import io.cucumber.java.fr.Alors
import io.cucumber.java.fr.Lorsque
import io.cucumber.java.fr.Étantdonnéque
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpMethod.GET
import java.time.LocalDate
import java.time.ZonedDateTime

class RecupererLesHorairesDeTrainsDUneStationDeTrains(
    private val partnerMocksRecord: PartnerMocksRecord,
    private val trainStationsRecord: TrainStationsRecord,
    private val testContext: TestContext,
    private val trainStationsClient: TrainStationsClient,
) {
    @Étantdonnéque("la fiche horaire du jour de cette station de trains est disponible chez le partenaire")
    fun `la fiche horaire du jour de cette station de trains est disponible chez le partenaire`() {
        val trainStationId = trainStationsRecord.findLastTrainStationId()

        val partnerMock = buildPartnerMockThatCouldFail(
            expectedUrlEquals = "/daily-departure-times?trainStationId=$trainStationId",
            expectedMethod = GET,
            expectedBody = listOf(
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay()),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(1)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(2)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(3)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(4)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(5)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(6)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(7)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(8)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(9)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(10)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(11)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(12)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(13)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(14)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(15)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(16)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(17)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(18)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(19)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(20)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(21)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(22)),
                xyzTrainDepartureResponseNominal.copy(departureTime = LocalDate.now().atStartOfDay().withHour(23)),
            ),
            shouldFail = false
        )

        partnerMocksRecord.addPartnerMock(partnerMock)
    }

    @Lorsque("je récupère les prochains départs de cette station de trains")
    fun `je récupère les prochains départs de cette station de trains`() {
        val trainStationId = trainStationsRecord.findLastTrainStationId()
        testContext.responseEntity = trainStationsClient.getTrainStationUpcomingDepartures(trainStationId)
    }

    @Alors("je reçois les prochains départs de cette station de trains")
    fun `je reçois les prochains départs de cette station de trains`() {
        testContext.assertStatusIsOk()

        val trainStationUpcomingDepartures = testContext.getResponseBodyAsListOf(TrainDepartureResponse::class)

        trainStationUpcomingDepartures
            .map(TrainDepartureResponse::departureTime)
            .filter { departureTime ->
                departureTime.isBefore(ZonedDateTime.now())
            }.size shouldBe 0
    }
}
