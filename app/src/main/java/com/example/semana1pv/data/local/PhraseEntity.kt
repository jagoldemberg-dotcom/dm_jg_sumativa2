package com.example.semana1pv.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class PhraseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val type: String, // "write" | "speak"
    val createdAt: Long = System.currentTimeMillis()
)
