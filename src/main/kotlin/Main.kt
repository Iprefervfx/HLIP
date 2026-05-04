import arg.entities.ExitCodes
import arg.parseArguments
import controller.entities.ResourceNodeDictionary
import controller.checkAccess
import controller.entities.ResourceNode
import controller.parseOperation
import db.authenticate
import db.loadPermissions
import db.loadResourceTree
import java.sql.SQLException
import java.sql.SQLNonTransientConnectionException
import kotlin.system.exitProcess

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    if (args.contains("--calc-hash")) {
        println(computeHash("qwerty", "gameSalt"))
        return
    }

    if (args.isEmpty()) {
        printHelp()
        exitProcess(ExitCodes.HELP_REQUESTED)
    }

    val parsed = parseArguments(args) ?: exitProcess(ExitCodes.INVALID_FORMAT)

    val root: ResourceNode = try {
        loadResourceTree()
    } catch (e: SQLNonTransientConnectionException) {
        exitProcess(ExitCodes.DB_CONNECTION_ERROR)
    } catch (e: SQLException) {
        exitProcess(ExitCodes.SQL_ERROR)
    }

    val acl = ResourceNodeDictionary()
    try {
        loadPermissions(acl, root)
    } catch (e: SQLNonTransientConnectionException) {
        exitProcess(ExitCodes.DB_CONNECTION_ERROR)
    } catch (e: SQLException) {
        exitProcess(ExitCodes.SQL_ERROR)
    }

    val authResult = authenticate(parsed.login, parsed.password)
    if (authResult != ExitCodes.SUCCESS) {
        exitProcess(authResult)
    }

    val op = parseOperation(parsed.action) ?: exitProcess(ExitCodes.UNKNOWN_OPERATION)

    val result = checkAccess(parsed.login, op, parsed.resource, parsed.volume, root, acl)
    exitProcess(result)
}