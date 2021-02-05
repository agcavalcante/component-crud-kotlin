package com.example.demo.controller

import com.example.demo.configuration.ExceptionsConstants
import com.example.demo.data.Component
import com.example.demo.data.ComponentRepository
import com.example.demo.exceptions.NoContentException
import com.example.demo.request.ComponentRequest
import com.example.demo.service.ComponentService
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/component")
class ComponentController(
    private val repository: ComponentRepository,
    private val componentService: ComponentService
) {

    @GetMapping
    fun getAllComponents(): MutableList<Component>? {
        val allReturned = repository.findAll()
            .ifEmpty { throw NoContentException(ExceptionsConstants.Exceptions.NO_CONTENT_EXCEPTION) }
        return componentService.resolveComponentToShow(allReturned)
    }

    @GetMapping("/{id}")
    fun getOneComponent(@PathVariable("id") id: String): Component {
        val oneReturnedComponent = repository.findOneById(ObjectId(id))
        return componentService.resolveOneComponentToShow(oneReturnedComponent)
    }

    @GetMapping("/group/{name}")
    fun getAllComponentsWithNameContains(@PathVariable("name") name: String): MutableList<Component>? {
        val allReturned = repository.findByNameContainingIgnoreCase(name)
            .ifEmpty { throw NoContentException(ExceptionsConstants.Exceptions.NO_CONTENT_EXCEPTION) }
        return componentService.resolveComponentToShow(allReturned)
    }

    @PutMapping("/{id}")
    fun patchOneComponent(
        @RequestBody request: ComponentRequest,
        @PathVariable("id") id: String
    ): ResponseEntity<Component> {
        val componentInDB = repository.findOneById(ObjectId(id))
        val componentToUpdate = repository.save(
            Component(
                id = componentInDB.id,
                name = request.name,
                manufacturer = request.manufacturer,
                isActive = componentInDB.isActive,
                quantity = request.quantity,
                group = request.group,
                value = request.value
            )
        )
        return ResponseEntity.ok(componentToUpdate)
    }

    @PostMapping
    fun postOneComponent(@RequestBody request: ComponentRequest): Component {
        val componentReceived = Component(
            name = request.name, manufacturer = request.manufacturer,
            isActive = true, quantity = request.quantity, group = request.group, value = request.value
        )
        return repository.save(componentService.verifyQuantityBeforeInsert(componentReceived))
    }

    @DeleteMapping("/{id}")
    fun deleteOneComponent(@PathVariable("id") id: String): ResponseEntity<Unit> {
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/inactive/{id}")
    fun inactiveOneComponent(@PathVariable("id") id: String): ResponseEntity<Unit> {
        val componentInDB = repository.findOneById(ObjectId(id))
        repository.save(
            Component(
                id = componentInDB.id,
                name = componentInDB.name,
                manufacturer = componentInDB.manufacturer,
                isActive = false,
                quantity = componentInDB.quantity,
                group = componentInDB.group,
                value = componentInDB.value
            )
        )
        return ResponseEntity.noContent().build()
    }

}