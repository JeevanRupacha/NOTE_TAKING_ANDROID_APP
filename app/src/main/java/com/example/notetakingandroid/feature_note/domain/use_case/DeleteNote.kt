package com.example.notetakingandroid.feature_note.domain.use_case

import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend fun execute(id: Int? = null)
    {
        if(id == null)
        {
            //delete all the note when no id is passed
            repository.deleteALlNote()

        }else{
            //Delete specific note with id only
            repository.deleteNoteById(id)
        }
    }
}