import arg.entities.ExitCodes
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun printHelp() {
    println("Система контроля доступа к ресурсам")
    println("Использование: resource-access --login <user> --password <pass> --action <operation> --resource <path> --volume <amount>")
    println()
    println("Аргументы:")
    println("  --login <user>       Идентификатор пользователя")
    println("  --password <pass>    Секрет для аутентификации")
    println("  --action <operation> Запрашиваемая операция: read, write, execute")
    println("  --resource <path>    Путь к ресурсу через точки (например, system.data.logs)")
    println("  --volume <amount>    Запрашиваемый объем ресурса (целое число)")
    println("  -h, --help           Показать эту справку")
    println()
    ExitCodes.printAllCodes()
}

fun computeHash(input: String, salt: String): String {
    val algorithm = MessageDigest.getInstance("SHA-256")
    val data = (salt + input).toByteArray(StandardCharsets.UTF_8)
    val digest = algorithm.digest(data)
    return digest.joinToString("") { "%02x".format(it) }
}