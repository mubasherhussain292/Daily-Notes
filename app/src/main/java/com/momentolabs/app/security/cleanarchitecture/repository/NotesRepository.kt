package com.momentolabs.app.security.cleanarchitecture.repository

import com.momentolabs.app.security.cleanarchitecture.db.NotesDao
import com.momentolabs.app.security.cleanarchitecture.models.Notes
import com.momentolabs.app.security.cleanarchitecture.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {
    suspend fun insetNotes(notes: Notes) {
        notesDao.insertNotes(notes)
    }


    suspend fun deleteNotes(userId: ArrayList<Int>) {
        notesDao.deleteByIdList(userId)
    }


    suspend fun getAllNotes(): Flow<Resources<List<Notes>>> = flow {
        emit(Resources.onLoading)

        try {

            notesDao.getAllNotes().collect { emit(Resources.onSuccess(it)) }

        } catch (e: Exception) {
            emit(Resources.onError(e))
        }
    }
}


private const val TAG = "NotesRepository"