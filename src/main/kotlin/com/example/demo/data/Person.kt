package com.example.demo.data

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Person(
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val sobrenome: String

) {
}