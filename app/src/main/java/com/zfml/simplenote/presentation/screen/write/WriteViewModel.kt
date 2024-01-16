package com.zfml.simplenote.presentation.screen.write

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.use_case.NoteUserCases
import com.zfml.simplenote.domain.util.Constants.WRITE_NOTE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val userCases: NoteUserCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private var currentNoteId: Int? = null

    var deletable = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(WRITE_NOTE_KEY)?.let { noteId ->
            if (noteId != -1) {
                deletable.value = true
                viewModelScope.launch {
                    userCases.getNoteById(noteId)?.let { note ->
                        currentNoteId = note.id
                        changeTitle(note.title)
                        changeDescription(note.description)
                    }
                }
            }


        }
    }


    private val _titleState = mutableStateOf(
        NoteTextFieldState(
            hint = "Title"
        )
    )
    val titleState: State<NoteTextFieldState> = _titleState


    private val _description = mutableStateOf(
        NoteTextFieldState(
            hint = "Description"
        )
    )
    val descriptionState: State<NoteTextFieldState> = _description

    private fun changeTitle(title: String) {
        _titleState.value = _titleState.value.copy(
            text = title,
            isHintVisible = false
        )
    }

    private fun onFocusTitleChanged(focusState: FocusState) {
        _titleState.value = _titleState.value.copy(
            isHintVisible = !focusState.isFocused && _titleState.value.text.isBlank()
        )
    }

    private fun changeDescription(description: String) {
        _description.value = _description.value.copy(
            text = description,
            isHintVisible = false
        )
    }

    private fun onFocusDescriptionChanged(focusState: FocusState) {
        _description.value = _description.value.copy(
            isHintVisible = !focusState.isFocused && _description.value.text.isBlank()
        )
    }

    fun onEvent(event: WriteEvent) {
        when (event) {
            is WriteEvent.ChangeDescription -> {
                changeDescription(event.description)
            }

            is WriteEvent.ChangeDescriptionFocus -> {
                onFocusDescriptionChanged(event.focusState)
            }

            is WriteEvent.ChangeTitle -> {
                changeTitle(event.title)
            }

            is WriteEvent.ChangeTitleFocus -> {
                onFocusTitleChanged(event.focusState)
            }

            is WriteEvent.DeleteNote -> {
                viewModelScope.launch {
                    userCases.deleteNote.invoke(
                        Note(
                            id = currentNoteId,
                            title = _titleState.value.text,
                            description = _description.value.text
                        )
                    )
                }
            }

            WriteEvent.SaveNote -> {
                viewModelScope.launch {
                    userCases.insertNote.invoke(
                        Note(
                            id = currentNoteId,
                            title = _titleState.value.text,
                            description = _description.value.text
                        )
                    )
                }

            }
        }
    }


    fun IsInvalidInput(
        title: String = _titleState.value.text,
        description: String = _description.value.text,
    ): Boolean {
        return title.isNotBlank() && description.isNotBlank()
    }


}