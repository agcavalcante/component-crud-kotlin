package com.example.demo.data

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PersonRepository: MongoRepository<Person, String> {
    fun findOneById(id: ObjectId): Person
    override fun deleteAll()
}