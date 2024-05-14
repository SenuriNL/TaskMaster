package com.example.thenotesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thenotesapp.model.Note

// Database class for Note entity, using Room
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    // Abstract function to get the DAO for Note
    abstract fun getNoteDao(): NoteDao

    companion object {
        // Volatile instance variable to ensure the database instance is always up-to-date and visible to all threads
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        // Operator function to get the database instance, creating it if necessary
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK) {
            instance ?:
            createDatabase(context).also {
                instance = it
            }
        }

        // Creates the Room database instance
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "todo_db"
            ).build()
    }
}
