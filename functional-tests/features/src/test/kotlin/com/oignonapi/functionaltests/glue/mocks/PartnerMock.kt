package com.oignonapi.functionaltests.glue.mocks

import com.oignonapi.functionaltests.glue.commons.Jackson
import okhttp3.mockwebserver.MockResponse
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

/**
 * `PartnerMock` est une classe d'aide à la création de bouchons partenaires.
 *
 * Exemples d'utilisation :
 *
 *     partnerMocksRecord.addPartnerMock(
 *         PartnerMock(
 *             containedUrl = "/mon-service-partenaire-1",
 *             returnedBodyAsObject = MyPartnerResponse(...),
 *             specificContentType = MediaType.APPLICATION_XML_VALUE
 *         )
 *     )
 *
 *     partnerMocksRecord.addPartnerMock(
 *         PartnerMock(
 *             containedUrl = "/mon-service-partenaire-2",
 *             completeMockResponse = MockResponse().setResponseCode(SERVICE_UNAVAILABLE.value())
 *         )
 *     )
 *
 * Lors de la création d'une nouvelle classe d'appel au partenaire dans la
 * couche infrastructure, ne pas oublier d'ajouter un Bean dans la classe
 * `WebClientMockConfiguration` afin de lui injecter le WebClient bouchonné.
 *
 * @param expectedUrlEquals URL complète du service partenaire que l'on souhaite bouchonner
 * @param expectedUrlContains URL partielle du service partenaire que l'on souhaite bouchonner
 * @param expectedBody Corps de la réponse sous forme d'objet
 * @param expectedContentType Type de contenu de la réponse si différent de `application/json`
 * @param mockedResponse Définition partielle ou complète d'un bouchon `MockResponse`, les autres
 *                     paramètres écrasent les propriétés de cette instance s'ils sont définis
 */
data class PartnerMock(
    val expectedUrlEquals: String? = null,
    val expectedUrlContains: String? = null,
    val expectedMethod: HttpMethod,
    val expectedBody: Any? = null,
    val expectedContentType: String? = null,
    val mockedResponse: MockResponse? = null,
) {
    companion object Factory {
        /**
         * Petite fonction utile pour créer rapidement un bouchon partenaire qui renvoie :
         * * `expectedBody` (ou Unit si non renseigné) si `shouldFail` est à `false`
         * * Une erreur 503 si `shouldFail` est à `true`
         */
        fun buildPartnerMockThatCouldFail(
            expectedUrlEquals: String? = null,
            expectedUrlContains: String? = null,
            expectedMethod: HttpMethod,
            expectedBody: Any? = Unit,
            shouldFail: Boolean,
        ): PartnerMock {

            val partnerMock = PartnerMock(
                expectedUrlEquals = expectedUrlEquals,
                expectedUrlContains = expectedUrlContains,
                expectedMethod = expectedMethod
            )

            return if (shouldFail) {
                partnerMock.copy(
                    mockedResponse = MockResponse().setResponseCode(SERVICE_UNAVAILABLE.value())
                )

            } else {
                partnerMock.copy(expectedBody = expectedBody)
            }
        }
    }

    fun urlEquals(requestPath: String?): Boolean {
        return expectedUrlEquals?.isNotEmpty() == true && expectedUrlEquals == requestPath
    }

    fun urlIsContainedIn(requestPath: String?): Boolean {
        return expectedUrlContains?.isNotEmpty() == true && requestPath?.contains(expectedUrlContains) == true
    }

    fun build(): MockResponse {
        val mockResponse = mockedResponse ?: MockResponse()

        if (expectedBody != null) {
            mockResponse.setBody(Jackson.write(expectedBody))
        }

        if (expectedContentType != null) {
            mockResponse.setHeader(CONTENT_TYPE, expectedContentType)
        } else {
            mockResponse.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        }

        return mockResponse
    }
}
