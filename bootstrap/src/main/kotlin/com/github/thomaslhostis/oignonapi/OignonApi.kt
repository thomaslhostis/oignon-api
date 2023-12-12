package com.github.thomaslhostis.oignonapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@SpringBootApplication
class OignonApi

fun main(args: Array<String>) {
    try {
        runApplication<OignonApi>(*args)
    }catch (e: Exception) {
        e.printStackTrace()
    }
}

@RestController
class Controller {
    @GetMapping("/home")
    fun getHome(): String {
        return "NOW!"
    }
}
