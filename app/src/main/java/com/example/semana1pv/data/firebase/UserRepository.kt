package com.example.semana1pv.data.firebase

import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val users = FirestoreService.db.collection(FirestoreService.COL_USERS)

    suspend fun upsert(profile: UserProfile) {
        users.document(profile.uid).set(profile, SetOptions.merge()).await()
    }

    suspend fun get(uid: String): UserProfile? =
        users.document(uid).get().await().toObject(UserProfile::class.java)
}
