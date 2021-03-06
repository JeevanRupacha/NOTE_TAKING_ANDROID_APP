package com.example.notetakingandroid.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notetakingandroid.feature_note.data.model.NoteEntity
import com.example.notetakingandroid.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE uid = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("DELETE FROM note WHERE uid = :id")
    suspend fun deleteNoteById(id: Int)

    @Query("DELETE FROM note ")
    suspend fun deleteAllNote()
}