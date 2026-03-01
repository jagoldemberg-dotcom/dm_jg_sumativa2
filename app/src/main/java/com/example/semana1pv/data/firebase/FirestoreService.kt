package com.example.semana1pv.data.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreService {
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    const val COL_USERS = "users"
    const val COL_PHRASES = "phrases"
    const val COL_DEVICES = "devices"
}
