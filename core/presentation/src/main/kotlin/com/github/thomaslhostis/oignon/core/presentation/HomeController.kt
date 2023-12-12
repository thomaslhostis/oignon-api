package com.github.thomaslhostis.oignon.core.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping("/home")
    fun getHome(): String {
        return "NOW!"
    }
}
