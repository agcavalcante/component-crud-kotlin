package com.example.demo.service

import com.example.demo.configuration.ExceptionsConstants
import com.example.demo.configuration.ExceptionsConstants.Exceptions.QUANTITY_EQUALS_OR_LOWER_THAN_ZERO
import com.example.demo.data.Component
import com.example.demo.exceptions.IllegalQuantityException
import com.example.demo.exceptions.NoContentException
import org.springframework.stereotype.Service

@Service
class ComponentService {

    fun resolveComponentToShow(components: MutableList<Component>): MutableList<Component>? {
        val componentListToReturn = mutableListOf<Component>()
        if (components.size != 0) {
            for (item in components.iterator()) {
                if (item.isActive == true) {
                    componentListToReturn.add(item)
                }
            }
        }

        if (componentListToReturn.size == 0)
            throw NoContentException(ExceptionsConstants.Exceptions.NO_CONTENT_EXCEPTION)
        return componentListToReturn
    }

    fun resolveOneComponentToShow(component: Component): Component {
        return if (component.isActive == true) {
            component
        } else {
            throw NoContentException(ExceptionsConstants.Exceptions.NO_CONTENT_EXCEPTION)
        }
    }

    fun verifyQuantityBeforeInsert(component: Component): Component {
//        return if (component.quantity > 0) {
//            component
//        } else {
//            throw IllegalQuantityException(QUANTITY_EQUALS_OR_LOWER_THAN_ZERO)
//        }
        when {
            component.quantity == 0 -> throw IllegalQuantityException(QUANTITY_EQUALS_OR_LOWER_THAN_ZERO)
            component.quantity!! < 0 -> throw IllegalQuantityException(QUANTITY_EQUALS_OR_LOWER_THAN_ZERO)
            else -> return component
        }
    }
}