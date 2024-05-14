package com.example.thenotesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.thenotesapp.model.Note

// Data Access Object (DAO) interface for Note entity
@Dao
interface NoteDao {

    // Inserts a note into the database, replacing any conflicting entries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // Updates an existing note in the database
    @Update
    suspend fun updateNote(note: Note)

    // Deletes a note from the database
    @Delete
    suspend fun deleteNote(note: Note)

    // Retrieves all notes from the database, ordered by ID in descending order
    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    // Searches for notes in the database where the name or description matches the query
    @Query("SELECT * FROM todos WHERE tdName LIKE :query OR tdDesc LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}
