package com.example.semana1pv.data.firebase

import kotlinx.coroutines.tasks.await
import kotlin.math.*

class DeviceRepository {

    private val devices = FirestoreService.db.collection(FirestoreService.COL_DEVICES)

    suspend fun add(remote: DeviceRemote) {
        devices.add(remote).await()
    }

    suspend fun all(): List<DeviceRemote> {
        val snap = devices.orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(200)
            .get().await()
        return snap.documents.mapNotNull { it.toObject(DeviceRemote::class.java) }
    }

    /** Filtro local (dataset pequeño) por distancia. */
    fun withinKm(list: List<DeviceRemote>, lat: Double, lng: Double, radiusKm: Double): List<DeviceRemote> {
        return list.filter { d -> haversineKm(lat, lng, d.lat, d.lng) <= radiusKm }
    }

    private fun haversineKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat/2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon/2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))
        return r * c
    }
}
