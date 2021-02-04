package com.example.demo.controller

import com.example.demo.data.Person
import com.example.demo.data.PersonRepository
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController (private val repository: PersonRepository){

    @GetMapping("/{id}")
    fun getOnePatient(@PathVariable("id") id: String): ResponseEntity<Person> = ResponseEntity.ok(repository.findOneById(
        ObjectId(id)
    ))
}