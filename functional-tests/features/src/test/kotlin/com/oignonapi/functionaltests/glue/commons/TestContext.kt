package com.oignonapi.functionaltests.glue.commons

import com.oignonapi.functionaltests.glue.mocks.PartnerMocksRecord
import io.kotest.matchers.shouldBe
import io.restassured.response.Response
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * Cette classe contient quelques méthodes utilitaires et permet de partager la réponse des
 * appels à l'API entre les différentes étapes des tests grâce au paramètre `response`.
 */
@Component
class TestContext(
    private val mockWebServer: MockWebServer,
    private val partnerMocksRecord: PartnerMocksRecord,
) {
    lateinit var response: Response
    fun triggerPartnerMocks() {
        // Déclenche le bouchonnage des appels partenaires définis dans les étapes précédentes
        mockWebServer.dispatcher = partnerMocksRecord.buildDispatcher()
    }

    fun <T : Any> getResponseBodyAsListOf(responseType: KClass<out T>): List<T> {
        return response.jsonPath().getList("", responseType.java)
    }

    fun assertStatusIsOk(expectedMessage: String? = null) {
        assertStatus(OK, expectedMessage)
    }

    fun assertStatus(
        expectedHttpStatus: HttpStatus,
        expectedErrorMessage: String? = null,
    ) {
        response.statusCode shouldBe expectedHttpStatus.value()
        if (expectedErrorMessage != null) {
            response.body.asString() shouldBe expectedErrorMessage
        }
    }
}
