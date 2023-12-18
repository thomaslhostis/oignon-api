package com.oignonapi.functionaltests

import com.oignonapi.OignonApi
import com.oignonapi.functionaltests.glue.configuration.ContextCleaner
import io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME
import io.cucumber.java.Before
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber.json")
class FunctionalTests {
    @SpringBootTest(classes = [OignonApi::class], webEnvironment = RANDOM_PORT)
    @CucumberContextConfiguration
    @ActiveProfiles(value = ["features"])
    class CucumberSpringConfiguration(
        private val contextCleaner: ContextCleaner,
    ) {
        @Before
        fun beforeEachScenario() {
            contextCleaner.cleanup()
        }
    }
}
