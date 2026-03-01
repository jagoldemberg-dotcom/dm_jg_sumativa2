package com.example.semana1pv.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.semana1pv.data.firebase.FirebaseAuthService
import com.example.semana1pv.data.firebase.PhraseRemote
import com.example.semana1pv.data.firebase.PhraseRepository
import com.example.semana1pv.data.local.AppDatabase
import com.example.semana1pv.data.local.PhraseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhraseViewModel(app: Application) : AndroidViewModel(app) {

    private val auth = FirebaseAuthService()
    private val phraseRepo = PhraseRepository()
    private val dao = AppDatabase.get(app).phraseDao()

    private val _latestLocal = MutableStateFlow<List<PhraseEntity>>(emptyList())
    val latestLocal: StateFlow<List<PhraseEntity>> = _latestLocal

    fun refreshLocal() {
        viewModelScope.launch { _latestLocal.value = dao.latest(20) }
    }

    fun save(text: String, type: String) {
        val trimmed = text.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            dao.insert(PhraseEntity(text = trimmed, type = type))
            refreshLocal()

            val uid = auth.currentUser?.uid
            if (uid != null) {
                // Si Firebase no está configurado, esta llamada podría fallar: lo ignoramos para no romper la UX.
                runCatching {
                    phraseRepo.add(PhraseRemote(uid = uid, text = trimmed, type = type))
                }
            }
        }
    }
}
