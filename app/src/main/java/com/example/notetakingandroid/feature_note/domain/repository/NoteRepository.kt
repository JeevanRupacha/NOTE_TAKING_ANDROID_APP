package com.example.notetakingandroid.feature_note.domain.repository

import com.example.notetakingandroid.feature_note.data.model.NoteEntity
import com.example.notetakingandroid.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getAllNotes() : Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteALlNote()
    suspend fun deleteNoteById(id: Int)
}