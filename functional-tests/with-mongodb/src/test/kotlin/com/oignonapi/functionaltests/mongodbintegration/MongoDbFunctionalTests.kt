package com.oignonapi.functionaltests.mongodbintegration

import com.oignonapi.OignonApi
import com.oignonapi.functionaltests.glue.configuration.ContextCleaner
import com.oignonapi.functionaltests.glue.configuration.RestAssuredConfiguration
import io.cucumber.core.options.Constants.*
import io.cucumber.java.Before
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.Suite
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "com.oignonapi.functionaltests.glue,com.oignonapi.functionaltests.mongodbintegration"
)
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "../features/src/test/resources")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber.json")
class MongoDbFunctionalTests {
    @SpringBootTest(classes = [OignonApi::class], webEnvironment = RANDOM_PORT)
    @CucumberContextConfiguration
    @ActiveProfiles(value = ["features", "mongo"])
    class CucumberSpringConfiguration(
        private val restAssuredConfiguration: RestAssuredConfiguration,
        private val contextCleaner: ContextCleaner,
    ) {
        @LocalServerPort
        private val port: Int = 0

        @Before
        fun beforeEachScenario() {
            restAssuredConfiguration.setup(port)
            contextCleaner.cleanup()
        }
    }
}
