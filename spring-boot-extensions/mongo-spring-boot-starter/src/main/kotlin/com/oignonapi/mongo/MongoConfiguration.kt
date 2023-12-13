package com.oignonapi.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("mongo")
class MongoConfiguration(
    @Value("\${spring.data.mongodb.uri}")
    private val mongoDbUri: String,
) {
    @Bean
    fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(mongoDbUri)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()

        return MongoClients.create(mongoClientSettings)
    }
}
