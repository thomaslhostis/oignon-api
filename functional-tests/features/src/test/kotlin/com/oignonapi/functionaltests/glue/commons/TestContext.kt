package com.oignonapi.functionaltests.glue.commons

import com.oignonapi.functionaltests.glue.mocks.PartnerMocksRecord
import io.kotest.matchers.shouldBe
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * Cette classe contient quelques méthodes utilitaires et permet de partager la réponse des
 * appels à l'API entre les différentes étapes des tests grâce au paramètre `responseEntity`.
 */
@Component
class TestContext(
    private val mockWebServer: MockWebServer,
    private val partnerMocksRecord: PartnerMocksRecord,
) {
    lateinit var responseEntity: ResponseEntity<*>

    companion object {
        fun getResponseType(
            shouldFail: String?,
            defaultResponseType: KClass<*>,
        ): Class<*> {
            return if (shouldFail != null) String::class.java
            else defaultResponseType.java
        }
    }

    fun triggerPartnerMocks() {
        // Déclenche le bouchonnage des appels partenaires définis dans les étapes précédentes
        mockWebServer.dispatcher = partnerMocksRecord.buildDispatcher()
    }

    fun <T : Any> getResponseBodyOf(responseType: KClass<out T>): T {
        return responseType.javaObjectType.cast(responseEntity.body)
    }

    fun <T : Any> getResponseBodyAsListOf(responseType: KClass<out T>): List<T> {
        return listOf(*responseEntity.body as Array<T>)
    }

    fun assertStatusIsOk(expectedMessage: String? = null) {
        assertStatus(OK, expectedMessage)
    }

    fun assertStatus(
        expectedHttpStatus: HttpStatus,
        expectedErrorMessage: String? = null,
    ) {
        responseEntity.statusCode shouldBe expectedHttpStatus
        if (expectedErrorMessage != null) {
            val actualMessage = getResponseBodyOf(String::class)
            actualMessage shouldBe expectedErrorMessage
        }
    }
}
