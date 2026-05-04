package controller.entities

import controller.enum.Operation

class UserOperationDictionary {
    private val rules = mutableMapOf<String, MutableSet<Operation>>()

    fun grant(user: String, operation: Operation) {
        rules.getOrPut(user) { mutableSetOf() }.add(operation)
    }

    fun isAllowed(user: String, operation: Operation): Boolean {
        return operation in (rules[user] ?: emptySet())
    }
}