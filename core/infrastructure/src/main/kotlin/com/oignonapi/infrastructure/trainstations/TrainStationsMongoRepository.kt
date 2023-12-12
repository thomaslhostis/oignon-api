package com.oignonapi.infrastructure.trainstations

import org.springframework.data.mongodb.repository.MongoRepository

interface TrainStationsMongoRepository : MongoRepository<TrainStationDocument, String>
