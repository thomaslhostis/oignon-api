package com.oignonapi.functionaltests.glue.configuration

import com.oignonapi.functionaltests.glue.features.trainstations.TrainStationsRecord
import com.oignonapi.functionaltests.glue.mocks.PartnerMocksRecord
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

/**
 * Cette classe contient la réinitialisation du contexte des tests fonctionnels. Le but est de ne
 * pas avoir à relancer l'application avant chaque test, car cela peut devenir chronophage, mais
 * de réinitialiser son état.
 */
@Component
class ContextCleaner(
    private val mongoTemplate: MongoTemplate,
    private val partnerMocksRecord: PartnerMocksRecord,
    private val trainStationsRecord: TrainStationsRecord,
) {
    fun cleanup() {
        for (collectionName in mongoTemplate.collectionNames) {
            mongoTemplate.getCollection(collectionName).drop()
        }
        partnerMocksRecord.reset()
        trainStationsRecord.reset()
    }
}
