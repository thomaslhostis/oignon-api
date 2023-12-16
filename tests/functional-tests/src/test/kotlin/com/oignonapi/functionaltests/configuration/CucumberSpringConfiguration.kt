package com.oignonapi.functionaltests.configuration

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    classes = [com.oignonapi.OignonApi::class],
    webEnvironment = RANDOM_PORT
)
@CucumberContextConfiguration
@ActiveProfiles(value = ["features"])
class CucumberSpringConfiguration
