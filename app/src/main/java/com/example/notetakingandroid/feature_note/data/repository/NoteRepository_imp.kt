package com.example.notetakingandroid.feature_note.data.repository

import com.example.notetakingandroid.feature_note.data.data_source.NoteDao
import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepository_imp(
    private val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteALlNote() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNoteById(id: Int) {
        TODO("Not yet implemented")
    }
}