package com.example.semana1pv.data.firebase

data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class PhraseRemote(
    val uid: String = "",
    val text: String = "",
    val type: String = "write",
    val createdAt: Long = System.currentTimeMillis()
)

data class DeviceRemote(
    val uid: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
