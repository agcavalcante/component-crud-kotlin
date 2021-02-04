package com.example.demo

import com.example.demo.data.Person
import com.example.demo.data.PersonRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTest @Autowired constructor (
    private val repository: PersonRepository,
    private val restTemplate: TestRestTemplate
) {
    private val defaultPersonId = "507f1f77bcf86cd799439011"

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    private fun getBaseUrl(): String? = "http://localhost:$port/person"
    private fun saveOnePerson() = repository.save(Person(ObjectId(defaultPersonId), "Gabriel", "Andrade"))
    //private fun personRequest() = PersonRequest("Name", "Description")

    @Test
    fun `should return a single patient by id`() {
        saveOnePerson()
        val response = restTemplate.getForEntity(getBaseUrl() + "/$defaultPersonId", Person::class.java)
        assertEquals(200, response.statusCode.value())
        assertEquals(defaultPersonId, response.body?.id)
    }

//    @Test
//    fun `should return a single patient by id`() {
//        saveOnePerson()
//        val response = restTemplate.exchange(getBaseUrl() + "/$defaultPersonId", HttpMethod.GET,
//            HttpEntity(null, HttpHeaders()), Person::class.java)
//        assertEquals(200, response.statusCode.value())
//        assertEquals(defaultPersonId, response.body?.id)
//    }

//    @Test
//    fun `should return a single patient by id`() {
//        saveOnePerson()
//        val personExecuteRequest = personRequest()
//        //val response = restTemplate.getForEntity(getBaseUrl() + "/$defaultPersonId", Person::class.java)
//        val response = repository.findOneById(ObjectId(defaultPersonId))
//        //assertEquals(200, response.statusCode.value())
//        assertEquals(defaultPersonId, response.id.toString())
//    }
}