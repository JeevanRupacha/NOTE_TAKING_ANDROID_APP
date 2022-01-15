package com.example.notetakingandroid.feature_note.domain.use_case

import com.example.notetakingandroid.feature_note.data.data_source.DataState
import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNote(
    private val repository: NoteRepository
) {

    operator fun invoke(id: Int): Flow<DataState<Note>> = flow{
        try{
            //emit(DataState.loading())

            val note = repository.getNoteById(id)

            if(note != null)
            {
                emit(DataState.success(data = note))
            }

        }catch (e: Exception)
        {
            emit(DataState.failure(message = e.message?:"Unknown error !"))
        }
    }
}