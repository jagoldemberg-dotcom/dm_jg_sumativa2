package com.example.semana1pv.data

import androidx.compose.runtime.mutableStateListOf

/**
 * Almacen en memoria.
 * Requisito: guardar maximo 5 usuarios desde la view de Registro.
 */
object UserStore {
    private const val MAX_USERS = 5

    // Lista observable (para que se refresque la UI)
    val users = mutableStateListOf<User>()

    fun addUser(user: User): Result<Unit> {
        if (users.size >= MAX_USERS) {
            return Result.failure(IllegalStateException("Solo se permiten $MAX_USERS usuarios."))
        }
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) {
            return Result.failure(IllegalArgumentException("El correo ya existe."))
        }
        users.add(user)
        return Result.success(Unit)
    }

    fun validateLogin(email: String, password: String): Boolean {
        return users.any { it.email.equals(email, ignoreCase = true) && it.password == password }
    }

    fun canRegisterMore(): Boolean = users.size < MAX_USERS
}
