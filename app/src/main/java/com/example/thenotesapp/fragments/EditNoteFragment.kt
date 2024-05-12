package com.example.thenotesapp.fragments

import android.app.AlertDialog
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
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thenotesapp.MainActivity
import com.example.thenotesapp.R
import com.example.thenotesapp.databinding.FragmentEditNoteBinding
import com.example.thenotesapp.model.Note
import com.example.thenotesapp.viewmodel.NoteViewModel
import java.util.Calendar

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editTdName.setText(currentNote.tdName)
        binding.editTdDesc.setText(currentNote.tdDesc)
        binding.editTdPriority.setText(currentNote.tdPriority)
        binding.editTdDeadline.setText(currentNote.tdDeadline)

        val chooseDateButton: Button = view.findViewById(R.id.chooseDateButton)
        chooseDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        binding.editNoteFab.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val tdName = binding.editTdName.text.toString().trim()
        val tdDesc = binding.editTdDesc.text.toString().trim()
        val tdPriority = binding.editTdPriority.text.toString().trim()
        val tdDeadline = binding.editTdDeadline.text.toString().trim()

        if (tdName.isNotEmpty()) {
            val note = Note(currentNote.id, tdName, tdDesc, tdPriority, tdDeadline)
            notesViewModel.updateNote(note)
            view?.findNavController()?.popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(context, "Please Enter Task Title", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Task")
            setMessage("Do you want to delete this Task?")
            setPositiveButton("Delete") { _, _ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, day ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$day"
            binding.editTdDeadline.setText(selectedDate)
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}
