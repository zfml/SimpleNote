package com.zfml.simplenote.data.repository

import com.zfml.simplenote.data.data_source.NoteDao
import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNote()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun upsertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    override fun searchNote(searchQuery: String): Flow<List<Note>> {
        return noteDao.searchNote(searchQuery = searchQuery)
    }
}