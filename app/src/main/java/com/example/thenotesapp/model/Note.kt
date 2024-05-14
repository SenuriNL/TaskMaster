package com.example.thenotesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Define a Note entity in the "todos" table
@Entity(tableName="todos")
@Parcelize
data class Note(
    // Primary key for the Note entity, auto-generated
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // Name of the to-do item
    val tdName: String,

    // Description of the to-do item
    val tdDesc: String,

    // Priority level of the to-do item
    val tdPriority: String,

    // Deadline for the to-do item
    val tdDeadline: String
) : Parcelable  // Implements Parcelable to allow passing Note objects between components
