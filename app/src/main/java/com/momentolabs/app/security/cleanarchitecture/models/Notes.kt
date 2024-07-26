package com.momentolabs.app.security.cleanarchitecture.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteTitle: String,
    val notesDescription: String,
//    val notesDate: Date
    val notesDate: String
)
