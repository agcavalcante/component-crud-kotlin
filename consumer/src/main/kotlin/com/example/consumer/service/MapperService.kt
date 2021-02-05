package com.example.consumer.service

import com.example.consumer.data.ComponentDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.awt.Component

class MapperService(val component: Component, val mapper: ObjectMapper) {

    fun remappingToComponent(content: MutableMap<String, String>) {
        val gettingMethod = content["method"]
        val componentMapped = mapper.readValue(content["content"], ComponentDTO::class.java)
    }
}