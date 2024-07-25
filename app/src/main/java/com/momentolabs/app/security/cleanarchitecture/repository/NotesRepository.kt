package com.momentolabs.app.security.cleanarchitecture.repository

import com.momentolabs.app.security.cleanarchitecture.db.NotesDao
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {
}