package com.oignonapi.functionaltests.mockedtests

import com.oignonapi.functionaltests.configuration.ContextCleaner
import io.cucumber.core.options.Constants.*
import io.cucumber.java.Before
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.Suite
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.oignonapi.functionaltests")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "../features/src/test/resources")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber.json")
class MockedFunctionalTests {
    @SpringBootTest(classes = [com.oignonapi.OignonApi::class], webEnvironment = RANDOM_PORT)
    @CucumberContextConfiguration
    @ActiveProfiles(value = ["features"])
    class CucumberSpringConfiguration(
        private val contextCleaner: ContextCleaner,
    ) {
        @Before
        fun beforeEveryScenario() {
            contextCleaner.cleanup()
        }
    }
}
