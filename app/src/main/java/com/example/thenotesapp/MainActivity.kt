package com.example.thenotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.thenotesapp.database.NoteDatabase
import com.example.thenotesapp.repository.NoteRepository
import com.example.thenotesapp.viewmodel.NoteViewModel
import com.example.thenotesapp.viewmodel.NoteViewModelFactory

// Main activity class for the application
class MainActivity : AppCompatActivity() {

    // ViewModel instance for managing notes
    lateinit var noteViewModel: NoteViewModel

    // Called when the activity is starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup ViewModel
        setupViewModel()
    }

    // Sets up the ViewModel for managing notes
    private fun setupViewModel() {
        // Creates a NoteRepository instance with the application context
        val noteRepository = NoteRepository(NoteDatabase(this))

        // Creates a ViewModelProviderFactory for the NoteViewModel
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        // Initializes the NoteViewModel using the ViewModelProvider with the ViewModelProviderFactory
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}
