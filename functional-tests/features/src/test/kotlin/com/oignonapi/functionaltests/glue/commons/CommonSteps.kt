package com.oignonapi.functionaltests.glue.commons

import io.cucumber.java.fr.Alors
import org.springframework.http.HttpStatus

/**
 * Contient les étapes génériques communes aux différentes fonctionnalités
 */
class CommonSteps(
    private val testContext: TestContext,
) {
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
