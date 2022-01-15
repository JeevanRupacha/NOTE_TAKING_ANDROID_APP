package com.example.notetakingandroid.feature_note.domain.use_case

class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote,
) {
}