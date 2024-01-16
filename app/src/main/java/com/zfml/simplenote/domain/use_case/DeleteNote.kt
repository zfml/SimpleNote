package com.zfml.simplenote.domain.use_case

import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.repository.NoteRepository


class DeleteNote(
    private val repository: NoteRepository
) {
    suspend  operator fun invoke(note: Note) {
            repository.deleteNote(note)
    }
}