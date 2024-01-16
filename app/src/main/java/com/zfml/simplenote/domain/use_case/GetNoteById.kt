package com.zfml.simplenote.domain.use_case

import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.repository.NoteRepository


class GetNoteById(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
          return  noteRepository.getNoteById(id)
    }
}