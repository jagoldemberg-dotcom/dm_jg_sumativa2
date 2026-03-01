package com.example.semana1pv.data.firebase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    val currentUser get() = auth.currentUser

    suspend fun login(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await().user

    suspend fun register(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password).await().user

    suspend fun sendPasswordReset(email: String) =
        auth.sendPasswordResetEmail(email).await()

    fun logout() = auth.signOut()
}
