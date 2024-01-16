package com.zfml.simplenote.domain.use_case

import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        withContext(Dispatchers.IO) {
            noteRepository.upsertNote(note)
        }
    }
}