package com.example.thenotesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName="todos")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tdName:String,
    val tdDesc: String,
    val tdPriority : String,
    val tdDeadline : String
):Parcelable
