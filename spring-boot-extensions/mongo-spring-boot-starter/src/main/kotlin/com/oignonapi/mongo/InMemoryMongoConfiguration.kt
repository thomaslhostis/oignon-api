package com.oignonapi.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
@Profile("!mongo")
class InMemoryMongoConfiguration {
    @Bean
    fun mongoUri(mongoServer: MongoServer): String {
        val serverAddress = mongoServer.localAddress
        return "mongodb://${serverAddress.hostName}:${serverAddress.port}/test-database"
    }

    @Bean
    fun mongoDbFactory(mongoUri: String): MongoDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(mongoUri)
    }

    @Bean
    fun mongoTemplate(mongoDatabaseFactory: MongoDatabaseFactory): MongoTemplate {
        return MongoTemplate(mongoDatabaseFactory)
    }

    @Bean(destroyMethod = "shutdown")
    fun mongoServer(): MongoServer {
        val mongoServer = MongoServer(MemoryBackend())
        mongoServer.bind()
        return mongoServer
    }

    @Bean
    fun mongoClient(mongoUri: String): MongoClient {
        val connectionString = ConnectionString(mongoUri)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString).build()
        return MongoClients.create(mongoClientSettings)
    }
}
