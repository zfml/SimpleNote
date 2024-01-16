package com.zfml.simplenote.domain.use_case

import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
}