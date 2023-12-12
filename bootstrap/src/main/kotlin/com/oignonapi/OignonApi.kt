package com.oignonapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OignonApi

fun main(args: Array<String>) {
    runApplication<OignonApi>(*args)
}
