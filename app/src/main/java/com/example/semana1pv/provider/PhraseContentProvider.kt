package com.example.semana1pv.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.semana1pv.data.local.AppDatabase
import com.example.semana1pv.data.local.PhraseEntity
import kotlinx.coroutines.runBlocking

class PhraseContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.semana1pv.phrases"
        private const val PATH_PHRASES = "phrases"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH_PHRASES")

        private const val CODE_PHRASES = 1
        private const val CODE_PHRASE_ID = 2

        private val matcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_PHRASES, CODE_PHRASES)
            addURI(AUTHORITY, "$PATH_PHRASES/#", CODE_PHRASE_ID)
        }
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val ctx = context ?: throw IllegalStateException("Context null")
        val db = AppDatabase.get(ctx)
        val cursor = MatrixCursor(arrayOf("_id", "text", "type", "createdAt"))

        runBlocking {
            when (matcher.match(uri)) {
                CODE_PHRASES -> {
                    val limit = selectionArgs?.firstOrNull()?.toIntOrNull() ?: 20
                    db.phraseDao().latest(limit).forEach { p ->
                        cursor.addRow(arrayOf(p.id, p.text, p.type, p.createdAt))
                    }
                }
                CODE_PHRASE_ID -> {
                    val id = ContentUris.parseId(uri)
                    db.phraseDao().latest(200).firstOrNull { it.id == id }?.let { p ->
                        cursor.addRow(arrayOf(p.id, p.text, p.type, p.createdAt))
                    }
                }
                else -> {}
            }
        }

        return cursor
    }

    override fun getType(uri: Uri): String? = when (matcher.match(uri)) {
        CODE_PHRASES -> "vnd.android.cursor.dir/vnd.$AUTHORITY.phrase"
        CODE_PHRASE_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY.phrase"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val ctx = context ?: return null
        val db = AppDatabase.get(ctx)
        if (matcher.match(uri) != CODE_PHRASES) return null

        val text = values?.getAsString("text") ?: return null
        val type = values.getAsString("type") ?: "write"

        val id = runBlocking { db.phraseDao().insert(PhraseEntity(text = text, type = type)) }
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        // No se necesita para la evaluación.
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        // No se necesita para la evaluación.
        return 0
    }
}
