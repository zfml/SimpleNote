package com.zfml.simplenote.presentation.screen.notes

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
        fetchAllNotes()
    }

    fun closeSearch() {
        _searchState.value = _searchState.value.copy(
            isClosed = true,
            isFocus = false,

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
        if (searchQuery.isNotBlank()) {
            getAllNotesBySearchQuery(searchQuery = searchQuery)
        } else {
            getAllNotes()
        }


    }


    private fun fetchAllNotes() {
        if (searchState.value.isFocus) {
            getAllNotesBySearchQuery(searchQuery = searchState.value.searchQuery)
        } else {
            getAllNotes()
        }
    }

    private fun getAllNotesBySearchQuery(searchQuery: String) {
        getAllNotesJob?.cancel()
        searchNotesJob = viewModelScope.launch {
            userCases.searchNote(searchQuery).collect { notes ->
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