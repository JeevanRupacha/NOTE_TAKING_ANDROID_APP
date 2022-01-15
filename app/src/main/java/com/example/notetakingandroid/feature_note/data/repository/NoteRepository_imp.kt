package com.example.notetakingandroid.feature_note.data.repository

import android.util.Log
import com.example.notetakingandroid.TAG
import com.example.notetakingandroid.feature_note.data.data_source.NoteDao
import com.example.notetakingandroid.feature_note.data.model.NoteEntity
import com.example.notetakingandroid.feature_note.data.model.NoteEntityMapper
import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class NoteRepository_imp(
    private val dao: NoteDao,
    private val mapper: NoteEntityMapper,
) : NoteRepository {
    @FlowPreview
    override suspend fun getAllNotes(): Flow<List<Note>> {
//        Log.d(TAG, "getAllNotes: ${dao.getAllNotes()}")
        return flowOf(mapper.mapFromEntityList(dao.getAllNotes().flattenToList()))
    }

    override suspend fun getNoteById(id: Int): Note? {
        val note = dao.getNoteById(id)
        return note?.let { mapper.mapToDomain(it)}
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(mapper.mapFromDomain(note))
    }

    override suspend fun deleteALlNote() {
        dao.deleteAllNote()
    }

    override suspend fun deleteNoteById(id: Int) {
        dao.deleteNoteById(id)
    }

    @FlowPreview
    suspend fun <T> Flow<List<T>>.flattenToList() = flatMapConcat { it.asFlow() }.toList()
}