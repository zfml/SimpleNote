package com.zfml.simplenote.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zfml.simplenote.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNote(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM Note WHERE id =:id")
    suspend fun getNoteById(id: Int): Note?

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note WHERE title LIKE '%'||:searchQuery ||'%'")
    fun searchNote(searchQuery: String): Flow<List<Note>>


}