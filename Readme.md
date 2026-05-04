# Лабораторная работа №6

## Авторы
- Тусюк Александр
- Зорин Олег

## Описание
Консольное приложение для управления доступом к иерархическим ресурсам с аутентификацией пользователей.  
Программа проверяет права доступа пользователя к указанному ресурсу и возвращает соответствующий код выполнения.

## Требования к окружению
- Java 11 или выше
- Kotlin compiler

Зависимости (библиотеки)  
В проекте используются следующие JAR-библиотеки, расположенные в папке `code/libs/`:

- kotlin-reflect-1.7.10.jar
- kotlin-stdlib.jar
- kotlinx-cli-jvm-0.3.6.jar
- h2-2.2.224.jar

# План БД для ЛР5

## Сущности и связи

**accounts**

- `login` — логин пользователя, первичный ключ.
- `salt` — соль для хеширования пароля.
- `password_hash` — хеш пароля (строка).

**resources**

- `id` — идентификатор ресурса, первичный ключ.
- `capacity` — максимальный объём ресурса (целое число).
- `parent_id` — ссылка на родительский ресурс (null для корня).

**permissions**

- `id` — первичный ключ (IDENTITY).
- `user_login` — логин пользователя, внешний ключ на `accounts.login`.
- `resource_id` — идентификатор ресурса, внешний ключ на `resources.id`.
- `operation` — тип операции: `READ`, `WRITE`, `EXECUTE`.

Связи:

- Один пользователь (`accounts`) может иметь много прав (`permissions`).
- Один ресурс (`resources`) может фигурировать во многих правах (`permissions`).
- Таблица `resources` задаёт дерево ресурсов через поле `parent_id`.

## Техническая сторона

- СУБД: H2, файловый режим.
- Драйвер: `h2-2.2.224.jar` (лежит в `libs/`).
- JDBC URL: `jdbc:h2:file:./scripts/appdb`
- Пользователь: `sa`, пароль: пустой.

Инициализация и заполнение:

- `scripts/init.sql` — создаёт таблицы `accounts`, `resources`, `permissions`.
- `scripts/gen-accounts.sh` — запускает приложение с `--calc-hash`, считает хеш пароля и генерирует `scripts/fill-accounts.sql` с `INSERT` в `accounts`.
- `scripts/fill.sql` — добавляет ресурсы и права (INSERT в `resources` и `permissions`).
- `scripts/init-db.sh` — по шагам:
    - собирает приложение (`code/build.sh`),
    - запускает `gen-accounts.sh`,
    - выполняет `init.sql`, `fill-accounts.sql`, `fill.sql` через `org.h2.tools.RunScript`.

---

## Изменения в программном коде

Новые (или изменённые) компоненты:

- `DbConfig` + `getConnection()`
    - Хранят URL, логин, пароль БД и создают `java.sql.Connection` через `DriverManager`.

- `AccountRepository`
    - Читает пользователя из таблицы `accounts` по логину.
    - Используется в функции `authenticate`.

- `ResourceRepository`
    - Загружает все строки из `resources`.
    - Строит дерево `ResourceNode` по `parent_id` и возвращает корень.

- `PermissionRepository`
    - Загружает все строки из `permissions`.
    - По `resource_id` находит узел в дереве и через `AccessControlService` выдаёт права.

Существующие доменные классы и логика:

- `Account`, `ResourceNode`, `Operation`, `AccessControlService`, `PathResolver`, `computeHash`, `authenticate`, `checkAccess`.
- Не знают о SQL и JDBC, работают только с моделями и сервисами.

`main` связывает всё вместе:

- Парсит аргументы.
- Создаёт репозитории и сервис ACL.
- Грузит ресурсы и права из БД (обрабатывает ошибки в коды 9 и 10).
- Аутентифицирует пользователя и проверяет доступ, завершаясь нужным exit‑кодом.

## ER‑диаграмма (Mermaid)

erDiagram
ACCOUNT {
string login
string salt
string password_hash
}
    RESOURCE {
        string id
        int capacity
        string parent_id
    }
    PERMISSION {
        int id
        string user_login
        string resource_id
        string operation
    }
    ACCOUNT ||--o{ PERMISSION : has
    RESOURCE ||--o{ PERMISSION : is_subject_of

    RESOURCE ||--o{ RESOURCE : parent_of
