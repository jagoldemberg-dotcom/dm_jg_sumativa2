package com.example.semana1pv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semana1pv.data.firebase.DeviceRemote
import com.example.semana1pv.data.firebase.DeviceRepository
import com.example.semana1pv.data.firebase.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeviceViewModel(
    private val auth: FirebaseAuthService = FirebaseAuthService(),
    private val repo: DeviceRepository = DeviceRepository()
) : ViewModel() {

    private val _devices = MutableStateFlow<List<DeviceRemote>>(emptyList())
    val devices: StateFlow<List<DeviceRemote>> = _devices

    fun loadAll() {
        viewModelScope.launch {
            _devices.value = runCatching { repo.all() }.getOrDefault(emptyList())
        }
    }

    fun add(name: String, lat: Double, lng: Double, onDone: () -> Unit = {}) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            val uid = auth.currentUser?.uid ?: "anon"
            runCatching { repo.add(DeviceRemote(uid = uid, name = trimmed, lat = lat, lng = lng)) }
            loadAll()
            onDone()
        }
    }

    fun filterNearby(lat: Double, lng: Double, radiusKm: Double): List<DeviceRemote> {
        return repo.withinKm(_devices.value, lat, lng, radiusKm)
    }
}
