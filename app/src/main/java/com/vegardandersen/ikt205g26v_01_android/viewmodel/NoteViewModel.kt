package com.vegardandersen.ikt205g26v_01_android.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vegardandersen.ikt205g26v_01_android.data.NoteRepository
import com.vegardandersen.ikt205g26v_01_android.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepository(application.applicationContext)

    val notes = mutableStateListOf<Note>()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            notes.clear()
            notes.addAll(repository.loadNotes())
        }
    }

    fun addNote(title: String, description: String) {
        val cleanTitle = title.trim()
        val cleanDescription = description.trim()

        if (cleanTitle.isEmpty() && cleanDescription.isEmpty()) return

        viewModelScope.launch {
            val note = Note(
                title = cleanTitle,
                description = cleanDescription
            )
            repository.addNote(note)
            notes.add(note)
        }
    }

    fun deleteNote(index: Int) {
        if (index !in notes.indices) return

        viewModelScope.launch {
            repository.deleteNote(index)
            notes.removeAt(index)
        }
    }
}