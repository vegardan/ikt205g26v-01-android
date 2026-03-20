package com.vegardandersen.ikt205g26v_01_android.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vegardandersen.ikt205g26v_01_android.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "fastnotes")

class NoteRepository(private val context: Context) {

    companion object {
        private val NOTES_KEY = stringPreferencesKey("notes")
    }

    private val json = Json

    suspend fun saveNotes(notes: List<Note>) {
        context.dataStore.edit { prefs ->
            prefs[NOTES_KEY] = json.encodeToString(notes)
        }
    }

    suspend fun loadNotes(): List<Note> {
        return context.dataStore.data
            .map { prefs ->
                val raw = prefs[NOTES_KEY] ?: "[]"
                try {
                    json.decodeFromString<List<Note>>(raw)
                } catch (_: Exception) {
                    emptyList()
                }
            }
            .first()
    }

    suspend fun addNote(note: Note) {
        val notes = loadNotes().toMutableList()
        notes.add(note)
        saveNotes(notes)
    }

    suspend fun deleteNote(index: Int) {
        val notes = loadNotes().toMutableList()
        if (index in notes.indices) {
            notes.removeAt(index)
            saveNotes(notes)
        }
    }
}
