import controller.entities.ResourceNode

class PathResolver {
    fun resolveFrom(start: ResourceNode, path: String): ResourceNode? {
        if (path.isEmpty()) return null
        val segments = path.split(".")
        if (segments.any { !ResourceNode.isValidIdentifier(it) }) return null

        var current: ResourceNode = start
        for (segment in segments) {
            current = current.findChild(segment) ?: return null
        }
        return current
    }
}