package com.momentolabs.app.security.cleanarchitecture.screens.createNotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momentolabs.app.security.cleanarchitecture.models.Notes
import com.momentolabs.app.security.cleanarchitecture.repository.NotesRepository
import com.momentolabs.app.security.cleanarchitecture.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repo: NotesRepository) : ViewModel() {

    private val _notes = MutableStateFlow<Resources<List<Notes>>>(Resources.onLoading)
    val allNotes: StateFlow<Resources<List<Notes>>> get() = _notes

    init {
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repo.getAllNotes().collect { resources ->
            _notes.value = resources
        }
    }

    fun insertNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repo.insetNotes(notes)
    }

}