package com.oignonapi.functionaltests.glue.mocks

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Component

/**
 * Cette classe permet, lors des étapes de préparation du contexte, d'accumuler des bouchons d'appels
 * partenaires afin de les déclencher juste avant l'appel à notre API. Elle est injectée dans les
 * différentes classes des tests fonctionnels pour partager son état entre ces classes.
 *
 * La liste des bouchons est réinitialisée dans la méthode `ContextCleaner.reset()` qui est appelée
 * avant chaque test.
 *
 * Exemple d'utilisation. Dans un premier temps, définir le bouchon :
 *
 *     partnerMocksRecord.addPartnerMock(
 *         PartnerMock(
 *             containedUrl = "...",
 *             returnedBodyAsObject = ...
 *         )
 *     )
 *
 * Puis, juste avant l'appel au backend MBG :
 *
 *     mockWebServer.dispatcher = partnerMocksRecord.buildDispatcher()
 *
 */
@Component
class PartnerMocksRecord(
    private var partnerMocks: MutableList<PartnerMock>,
) {
    fun reset() {
        partnerMocks = mutableListOf()
    }

    fun replaceOrAddPartnerMock(partnerMock: PartnerMock) {
        partnerMocks.removeIf { existingPartnerMock ->
            val partnerMockExistsByUrlEquality = partnerMock.expectedUrlEquals?.isNotEmpty() == true
                    && existingPartnerMock.expectedUrlEquals == partnerMock.expectedUrlEquals

            val partnerMockExistsByUrlComposition = partnerMock.expectedUrlContains?.isNotEmpty() == true
                    && existingPartnerMock.expectedUrlContains == partnerMock.expectedUrlContains

            partnerMockExistsByUrlEquality || partnerMockExistsByUrlComposition
        }
        addPartnerMock(partnerMock)
    }

    /**
     * Contrairement à [replaceOrAddPartnerMock], [addPartnerMock] permet de définir
     * plusieurs réponses pour une même URL d'appel. Cela permet de faire plusieurs
     * fois le même appel et d'obtenir séquentiellement une réponse différente.
     */
    fun addPartnerMock(partnerMock: PartnerMock) {
        partnerMocks.add(partnerMock.copy())
    }

    /**
     * Le `Dispatcher` permet de définir un bouchon spécifique à une URL lorsqu'on
     * utilise `MockWebServer` pour bouchonner les appels partenaires.
     *
     * Si on a plusieurs bouchons définis pour une même URL, on renvoie le premier
     * de la liste et on le supprime de la liste. Cela permet d'appeler plusieurs
     * fois un même service partenaire et d'obtenir une réponse différente à chaque
     * appel.
     */
    fun buildDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val foundPartnerMocks = partnerMocks.filter { partnerMock ->
                partnerMock.expectedMethod == HttpMethod.valueOf(request.method.orEmpty()) &&
                        (partnerMock.urlEquals(request.path) || partnerMock.urlIsContainedIn(request.path))
            }

            val firstPartnerMock = foundPartnerMocks.firstOrNull()

            if (foundPartnerMocks.size > 1) {
                partnerMocks.remove(firstPartnerMock)
            }

            return firstPartnerMock
                ?.build()
                ?: MockResponse()
                    .setResponseCode(NOT_FOUND.value())
                    .setBody("Appel partenaire non bouchonné : ${request.path}")
        }
    }
}
