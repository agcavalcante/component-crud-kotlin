package com.example.demo.configuration.rabbitmq

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rabbitmq.client.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitmqConfiguration(@Value("\${spring.rabbit.url}") private val URL: String,
                            @Value("\${spring.rabbit.queue}") private val QUEUE_NAME: String){

    fun send(message: MutableMap<String, String>) {
        val factory =  ConnectionFactory()
        factory.newConnection(URL).use {
                connection -> connection.createChannel().use {
                channel -> channel.queueDeclare(QUEUE_NAME, false, false, false, null )
            channel.basicPublish(
                "", QUEUE_NAME, null, jacksonObjectMapper().writeValueAsBytes(message)
            )
            println("[x] Sent '$message")
        }
        }
    }

}