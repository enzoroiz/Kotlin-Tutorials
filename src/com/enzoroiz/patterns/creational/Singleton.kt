package com.enzoroiz.patterns.creational

fun main(args: Array<String>) {
    println(NetworkConnection)
    println(NetworkConnection.networkAlias)

    NetworkConnection.networkAlias = "My Network"
    println(NetworkConnection.networkAlias)
    println()

    var database = Database.getInstance("MyDatabase")
    println(database)
    println(database.databaseName)
    database = Database.getInstance("YourDatabase") // Won't "update" the name as the singleton instance was already created
    println(database)
    println(database.databaseName)
}

// Bill Pugh Singleton
object NetworkConnection {
    var networkAlias: String? = null
}

// Kotlin Singleton with parameters
class Database private constructor(val databaseName: String) {
    companion object : SingletonHolder<Database, String>(::Database)
}

open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}