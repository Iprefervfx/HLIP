package controller.entities

class ResourceNode(
    val id: String,
    val capacity: Int = 10,
    var parent: ResourceNode? = null
) {
    private val children = mutableMapOf<String, ResourceNode>()

    fun addChild(node: ResourceNode) {
        children[node.id] = node
        node.parent = this
    }

    fun findChild(id: String): ResourceNode? = children[id]

    fun children(): Collection<ResourceNode> = children.values

    companion object {
        fun isValidIdentifier(name: String): Boolean {
            if (name.isEmpty() || name.length > 20) return false
            return name.all { it.isLetterOrDigit() || it == '_' }
        }
    }
}