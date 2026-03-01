package com.example.semana1pv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhraseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(phrase: PhraseEntity): Long

    @Query("SELECT * FROM phrases ORDER BY createdAt DESC LIMIT :limit")
    suspend fun latest(limit: Int = 20): List<PhraseEntity>

    @Query("SELECT * FROM phrases ORDER BY createdAt DESC LIMIT 1")
    suspend fun last(): PhraseEntity?

    @Query("DELETE FROM phrases")
    suspend fun clear()
}
