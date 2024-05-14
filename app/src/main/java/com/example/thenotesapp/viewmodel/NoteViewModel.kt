package com.example.thenotesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenotesapp.model.Note
import com.example.thenotesapp.repository.NoteRepository
import kotlinx.coroutines.launch

// ViewModel class for managing the UI-related data in a lifecycle-conscious way
class NoteViewModel(app: Application, private val noteRepository: NoteRepository) : AndroidViewModel(app) {

    // Adds a new note by launching a coroutine in the viewModelScope
    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    // Updates an existing note by launching a coroutine in the viewModelScope
    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    // Deletes an existing note by launching a coroutine in the viewModelScope
    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    // Retrieves all notes from the repository
    fun getAllNotes() = noteRepository.getAllNotes()

    // Searches for notes based on a query string
    fun searchNote(query: String?) = noteRepository.searchNote(query)
}
