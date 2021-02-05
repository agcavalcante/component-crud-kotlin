package com.example.demo.sender

import com.example.demo.configuration.rabbitmq.RabbitmqConfiguration
import org.springframework.stereotype.Component

@Component
class SendMessage(private val rabbitmqConfiguration: RabbitmqConfiguration) {
    fun send(component: MutableMap<String, String>) {
        rabbitmqConfiguration.send(component)
    }
}
