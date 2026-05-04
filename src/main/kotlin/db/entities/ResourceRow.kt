package db.entities

data class ResourceRow(
    val id: String,
    val capacity: Int,
    val parentId: String?
)