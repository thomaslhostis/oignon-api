package com.oignonapi.functionaltests.configuration

import com.oignonapi.functionaltests.features.trainstations.TrainStationsRecord
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * Cette classe contient la réinitialisation du contexte des tests fonctionnels. Le but est de ne
 * pas avoir à relancer l'application avant chaque test car cela peut devenir chronophage, mais
 * de réinitialiser son état.
 */
@Component
class ContextCleaner(
    private val testRestTemplate: RestTemplate,
    private val trainStationsRecord: TrainStationsRecord,
) {
    fun cleanContext() {
        testRestTemplate.interceptors = emptyList()
        trainStationsRecord.reset()
    }
}
