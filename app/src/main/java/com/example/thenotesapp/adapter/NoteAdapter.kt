package com.example.thenotesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thenotesapp.databinding.NoteLayoutBinding
import com.example.thenotesapp.fragments.HomeFragmentDirections
import com.example.thenotesapp.model.Note

// Adapter class for managing the RecyclerView of notes
class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // ViewHolder class for the note item view
    class NoteViewHolder(val itemBinding: NoteLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // Callback for calculating the difference between two non-null items in a list.
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        // Checks if two items are the same
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.tdDesc == newItem.tdDesc &&
                    oldItem.tdName == newItem.tdName &&
                    oldItem.tdPriority == newItem.tdPriority &&
                    oldItem.tdDeadline == newItem.tdDeadline
        }

        // Checks if the content of two items is the same
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    // Differ for asynchronously calculating the difference between lists
    val differ = AsyncListDiffer(this, differCallback)

    // Inflating the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    // Returns the size of the list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Binding data to the views of each item in the RecyclerView
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        // Setting the text for the views in the item layout
        holder.itemBinding.tdName.text = currentNote.tdName
        holder.itemBinding.tdDesc.text = currentNote.tdDesc
        holder.itemBinding.tdPriority.text = currentNote.tdPriority
        holder.itemBinding.tdDeadline.text = currentNote.tdDeadline

        // Setting an onClickListener to navigate to the EditNoteFragment when an item is clicked
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }
}
