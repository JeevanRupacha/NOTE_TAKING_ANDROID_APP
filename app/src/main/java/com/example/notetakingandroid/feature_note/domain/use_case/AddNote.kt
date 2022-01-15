package com.example.notetakingandroid.feature_note.domain.use_case

import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository
import com.example.notetakingandroid.feature_note.domain.util.InvalidNoteException

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note)
    {
        if(note.title.isBlank()){
            throw InvalidNoteException("The title must not be empty !")
        }

        if(note.content.isBlank()){
            throw InvalidNoteException("The content must not be empty !")
        }

        repository.insertNote(note)
    }
}