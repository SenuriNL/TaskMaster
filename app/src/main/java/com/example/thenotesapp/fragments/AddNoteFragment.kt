package com.example.thenotesapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.thenotesapp.MainActivity
import com.example.thenotesapp.R
import com.example.thenotesapp.databinding.FragmentAddNoteBinding
import com.example.thenotesapp.model.Note
import com.example.thenotesapp.viewmodel.NoteViewModel
import java.util.Calendar

// Fragment class for adding a new note
class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View

    // Inflating the fragment's layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Setting up the fragment's view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = (activity as MainActivity).noteViewModel
        addNoteView = view

        // Setting up the options menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Setting up the date picker dialog
        val chooseDateButton: Button = view.findViewById(R.id.chooseDateButton)
        chooseDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    // Showing the date picker dialog
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, day ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$day"
            binding.addTdDeadline.setText(selectedDate)
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    // Saving the note to the database
    private fun saveNote() {
        val tdName = binding.addTdName.text.toString().trim()
        val tdDesc = binding.addTdDesc.text.toString().trim()
        val tdPriority = binding.addTdPriority.text.toString().trim()
        val tdDeadline = binding.addTdDeadline.text.toString().trim()

        if (tdName.isNotEmpty()) {
            val note = Note(0, tdName, tdDesc, tdPriority, tdDeadline)
            notesViewModel.addNote(note)

            Toast.makeText(addNoteView.context, "Task Saved", Toast.LENGTH_SHORT).show()

            view?.findNavController()?.popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(addNoteView.context, "Please Enter Task title", Toast.LENGTH_SHORT).show()
        }
    }

    // Creating the options menu
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    // Handling menu item selections
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote()
                true
            }
            else -> false
        }
    }

    // Cleaning up when the fragment is destroyed
    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }
}
