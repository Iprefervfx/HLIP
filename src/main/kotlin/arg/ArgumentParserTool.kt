package arg

import arg.entities.ExitCodes
import arg.entities.ParsedArgs
import controller.entities.ResourceNode
import kotlinx.cli.*
import printHelp
import kotlin.system.exitProcess

fun parseArguments(args: Array<String>): ParsedArgs? {
    if (args.contains("-h") || args.contains("--help")) {
        printHelp()
        exitProcess(ExitCodes.HELP_REQUESTED)
    }

    val parser = ArgParser("resource-access")
    val username by parser.option(ArgType.String, fullName = "login").required()
    val secret by parser.option(ArgType.String, fullName = "password").required()
    val operation by parser.option(ArgType.String, fullName = "action").required()
    val path by parser.option(ArgType.String, fullName = "resource").required()
    val amount by parser.option(ArgType.String, fullName = "volume").required()

    try {
        parser.parse(args)
    } catch (e: Exception) {
        return null
    }

    if (path.split(".").any { !ResourceNode.isValidIdentifier(it) }) return null
    val volumeValue = amount.toIntOrNull() ?: return null

    return ParsedArgs(username, secret, operation, path, volumeValue)
}