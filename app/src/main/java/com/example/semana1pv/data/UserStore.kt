package com.example.semana1pv.data

import androidx.compose.runtime.mutableStateListOf

/**
 * Almacen en memoria.
 *
 * Requisito Sumativa 2:
 * - Mantener un ARRAY en Kotlin con hasta 5 usuarios y sus contraseñas.
 * - La UI se alimenta desde una lista observable (SnapshotStateList) para refrescar pantalla.
 */
object UserStore {
    private const val MAX_USERS = 5

    // Array "fuente de verdad" (requisito)
    private val usersArray: Array<User?> = arrayOfNulls(MAX_USERS)

    // Lista observable para Compose (refresco UI)
    val users = mutableStateListOf<User>()

    /** Devuelve una copia del array (para evidenciar el requisito). */
    fun asArray(): Array<User?> = usersArray.copyOf()

    fun addUser(user: User): Result<Unit> {
        val current = usersArray.filterNotNull()

        if (current.size >= MAX_USERS) {
            return Result.failure(IllegalStateException("Solo se permiten $MAX_USERS usuarios."))
        }
        if (current.any { it.email.equals(user.email, ignoreCase = true) }) {
            return Result.failure(IllegalArgumentException("El correo ya existe."))
        }

        val slot = usersArray.indexOfFirst { it == null }
        if (slot == -1) {
            return Result.failure(IllegalStateException("No hay cupos disponibles."))
        }

        usersArray[slot] = user
        syncStateList()
        return Result.success(Unit)
    }

    fun validateLogin(email: String, password: String): Boolean {
        val cleanEmail = email.trim()
        return usersArray
            .filterNotNull()
            .any { it.email.equals(cleanEmail, ignoreCase = true) && it.password == password }
    }

    fun canRegisterMore(): Boolean = usersArray.any { it == null }

    fun count(): Int = usersArray.count { it != null }

    private fun syncStateList() {
        users.clear()
        users.addAll(usersArray.filterNotNull())
    }
}
