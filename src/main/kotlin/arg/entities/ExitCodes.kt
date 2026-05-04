package arg.entities

object ExitCodes {
    const val SUCCESS = 0
    const val HELP_REQUESTED = 1
    const val INVALID_PASSWORD = 2
    const val INVALID_LOGIN = 3
    const val UNKNOWN_OPERATION = 4
    const val ACCESS_DENIED = 5
    const val RESOURCE_NOT_FOUND = 6
    const val INVALID_FORMAT = 7
    const val EXCEEDED_CAPACITY = 8
    const val DB_CONNECTION_ERROR = 9
    const val SQL_ERROR = 10

    fun getDescription(code: Int): String {
        return when (code) {
            SUCCESS -> "Успешное выполнение"
            HELP_REQUESTED -> "Запрошена справка"
            INVALID_PASSWORD -> "Неверный пароль"
            INVALID_LOGIN -> "Неверный логин"
            UNKNOWN_OPERATION -> "Неизвестное действие над ресурсом"
            ACCESS_DENIED -> "Нет доступа"
            RESOURCE_NOT_FOUND -> "Несуществующий ресурс"
            INVALID_FORMAT -> "Некорректный формат ресурса или объема"
            EXCEEDED_CAPACITY -> "Превышение максимального объема"
            DB_CONNECTION_ERROR -> "Ошибка подключения к базе данных"
            SQL_ERROR -> "Ошибка SQL-запроса"
            else -> "Неизвестный код возврата"
        }
    }

    fun printAllCodes() {
        println("Коды возврата (exit codes):")
        println("  $SUCCESS - ${getDescription(SUCCESS)}")
        println("  $HELP_REQUESTED - ${getDescription(HELP_REQUESTED)}")
        println("  $INVALID_PASSWORD - ${getDescription(INVALID_PASSWORD)}")
        println("  $INVALID_LOGIN - ${getDescription(INVALID_LOGIN)}")
        println("  $UNKNOWN_OPERATION - ${getDescription(UNKNOWN_OPERATION)}")
        println("  $ACCESS_DENIED - ${getDescription(ACCESS_DENIED)}")
        println("  $RESOURCE_NOT_FOUND - ${getDescription(RESOURCE_NOT_FOUND)}")
        println("  $INVALID_FORMAT - ${getDescription(INVALID_FORMAT)}")
        println("  $EXCEEDED_CAPACITY - ${getDescription(EXCEEDED_CAPACITY)}")
        println("  $DB_CONNECTION_ERROR - ${getDescription(DB_CONNECTION_ERROR)}")
        println("  $SQL_ERROR - ${getDescription(SQL_ERROR)}")
    }
}