package db.entities

data class Account(val salt: String, val hash: String)