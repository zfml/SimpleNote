package com.zfml.simplenote.domain.use_case

import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class SearchNote(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(searchQuery: String): Flow<List<Note>> {
        return noteRepository.searchNote(searchQuery = searchQuery)
    }
}