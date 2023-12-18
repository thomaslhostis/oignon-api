package com.oignonapi.functionaltests.glue.commons

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Jackson {
    fun write(any: Any): String {
        return jacksonObjectMapper()
            // Permet de sérialiser les dates au format ISO au lieu du format timestamp
            .registerModule(JavaTimeModule()).disable(WRITE_DATES_AS_TIMESTAMPS)
            // Permet d'ignorer les champs qui sont à `null` afin de ne pas les transformer en `NullNode`
            .setSerializationInclusion(NON_NULL)
            .writeValueAsString(any)
    }
}
