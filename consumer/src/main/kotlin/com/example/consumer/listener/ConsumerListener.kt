package com.example.consumer.listener

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ConsumerListener() {

    @RabbitListener(queues = ["\${spring.rabbit.queue}"])
    fun receivedMessage(msg: String) {
        println(msg)
    }

}
