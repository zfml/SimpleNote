package com.zfml.simplenote.presentation.screen.write

import androidx.compose.ui.focus.FocusState

sealed class WriteEvent {
    data class ChangeTitle(val title: String) : WriteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : WriteEvent()

    data class ChangeDescription(val description: String) : WriteEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState) : WriteEvent()

    object DeleteNote : WriteEvent()

    object SaveNote : WriteEvent()


}