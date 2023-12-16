package com.oignonapi.functionaltests.features

import io.cucumber.java.fr.Étantdonnéque
import org.assertj.core.api.Assertions.assertThat
import org.springframework.web.client.RestTemplate

class LancerLApplication(private val testRestTemplate: RestTemplate) {
    @Étantdonnéque("je veux lancer l'application")
    fun jeVeuxLancerLApplication() {
        val trainStations = testRestTemplate.getForEntity("/train-stations", String::class.java)
        assertThat(trainStations.body).isEqualTo("[]")
    }
}
