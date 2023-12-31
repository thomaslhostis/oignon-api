package com.oignonapi.functionaltests.glue.configuration

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType.JSON
import org.springframework.stereotype.Component

@Component
class RestAssuredConfiguration {

    fun setup(port: Int) {
        RestAssured.port = port
        RestAssured.requestSpecification = RequestSpecBuilder()
            .setContentType(JSON)
            .build()
    }
}
