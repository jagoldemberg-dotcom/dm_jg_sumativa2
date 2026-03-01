package com.example.semana1pv.data.firebase

import kotlinx.coroutines.tasks.await

class PhraseRepository {

    private val phrases = FirestoreService.db.collection(FirestoreService.COL_PHRASES)

    suspend fun add(remote: PhraseRemote) {
        phrases.add(remote).await()
    }

    suspend fun latestForUser(uid: String, limit: Long = 20): List<PhraseRemote> {
        val snap = phrases.whereEqualTo("uid", uid)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(limit)
            .get().await()

        return snap.documents.mapNotNull { it.toObject(PhraseRemote::class.java) }
    }
}
