package controller.entities

import controller.enum.Operation

class ResourceNodeDictionary {
    private val nodeAcls = mutableMapOf<ResourceNode, UserOperationDictionary>()

    fun grant(node: ResourceNode, user: String, operation: Operation) {
        nodeAcls.getOrPut(node) { UserOperationDictionary() }.grant(user, operation)
    }

    fun isPermitted(node: ResourceNode, user: String, operation: Operation): Boolean {
        var current: ResourceNode? = node
        while (current != null) {
            val acl = nodeAcls[current]
            if (acl?.isAllowed(user, operation) == true) {
                return true
            }
            current = current.parent
        }
        return false
    }
}