package com.zfml.simplenote.presentation.screen.notes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.use_case.NoteUserCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userCases: NoteUserCases,
) : ViewModel() {


    private var searchNotesJob: Job? = null
    private var getAllNotesJob: Job? = null

    private val _searchState = mutableStateOf(SearchUiState())
    val searchState: State<SearchUiState> = _searchState

    private val _notesUiState = MutableStateFlow(NotesUiState.Success(emptyList()))
    val noteUiState: StateFlow<NotesUiState> = _notesUiState.asStateFlow()


    init {
        getAllNotes()
    }

    fun closeSearch() {
        _searchState.value = _searchState.value.copy(
            isClosed = true,
            isFocus = false,
            searchQuery = ""
            )
    }

    fun onFocusSearch(focusState: FocusState) {
        _searchState.value = _searchState.value.copy(
            isFocus = focusState.isFocused
        )
    }

    fun onSearchQueryChange(searchQuery: String) {
        _searchState.value = _searchState.value.copy(
            searchQuery = searchQuery
        )
        getAllFilterNotes(searchQuery = searchQuery.trim())
    }

    fun getAllFilterNotes(searchQuery: String) {
        if (searchQuery.isNotBlank()) {
            getAllNotesBySearchQuery(searchQuery = searchQuery.trim())
        } else {
            getAllNotes()
        }
    }



    private fun getAllNotesBySearchQuery(searchQuery: String) {
        getAllNotesJob?.cancel()
        searchNotesJob = viewModelScope.launch {
            userCases.searchNote(searchQuery.trim()).collect { notes ->
                _notesUiState.value = NotesUiState.Success(notes)
            }
        }
    }

    private fun getAllNotes() {
        searchNotesJob?.cancel()

            getAllNotesJob = viewModelScope.launch {
                userCases.getNotes().collect { notes ->
                    _notesUiState.value = NotesUiState.Success(notes)
                }
            }

    }


}

data class SearchUiState(
    val isClosed: Boolean = true,
    val isFocus: Boolean = false,
    val searchQuery: String = "",
)

sealed class NotesUiState {
    data class Success(val notes: List<Note>) : NotesUiState()
    data class Error(val exception: Throwable) : NotesUiState()
}