package com.zfml.simplenote.domain.repository

import com.zfml.simplenote.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun upsertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun searchNote(searchQuery: String): Flow<List<Note>>

}