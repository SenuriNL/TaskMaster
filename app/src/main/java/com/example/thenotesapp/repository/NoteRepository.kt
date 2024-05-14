package com.example.thenotesapp.repository

import com.example.thenotesapp.database.NoteDatabase
import com.example.thenotesapp.model.Note

// Repository class for managing data operations related to notes
class NoteRepository(private val db: NoteDatabase) {

    // Inserts a new note into the database
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)

    // Deletes an existing note from the database
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    // Updates an existing note in the database
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    // Retrieves all notes from the database
    fun getAllNotes() = db.getNoteDao().getAllNotes()

    // Searches for notes in the database based on a query
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)

}
