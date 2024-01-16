package com.zfml.simplenote.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zfml.simplenote.data.data_source.NoteDatabase
import com.zfml.simplenote.data.data_source.NoteDatabase.Companion.NOTE_DATABASE_NAME
import com.zfml.simplenote.data.repository.NoteRepositoryImpl
import com.zfml.simplenote.domain.repository.NoteRepository
import com.zfml.simplenote.domain.use_case.DeleteNote
import com.zfml.simplenote.domain.use_case.GetNoteById
import com.zfml.simplenote.domain.use_case.GetNotes
import com.zfml.simplenote.domain.use_case.InsertNote
import com.zfml.simplenote.domain.use_case.NoteUserCases
import com.zfml.simplenote.domain.use_case.SearchNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): NoteDatabase{
        return Room.databaseBuilder(
            context = app,
            NoteDatabase::class.java,
            NOTE_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db : NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUserCases{
        return NoteUserCases(
            deleteNote = DeleteNote(noteRepository),
            getNoteById = GetNoteById(noteRepository),
            getNotes = GetNotes(noteRepository),
            insertNote = InsertNote(noteRepository),
            searchNote = SearchNote(noteRepository)
        )
    }


}