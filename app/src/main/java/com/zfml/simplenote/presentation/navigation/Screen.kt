package com.zfml.simplenote.presentation.navigation

import com.zfml.simplenote.domain.util.Constants.WRITE_NOTE_KEY

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_route")
    object Write : Screen(route = "write_route?$WRITE_NOTE_KEY={$WRITE_NOTE_KEY}") {
        fun passById(noteId: Int) = "write_route?$WRITE_NOTE_KEY=$noteId"

    }
}