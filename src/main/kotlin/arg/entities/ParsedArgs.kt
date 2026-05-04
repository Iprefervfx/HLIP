package arg.entities

data class ParsedArgs(
    val login: String,
    val password: String,
    val action: String,
    val resource: String,
    val volume: Int
)