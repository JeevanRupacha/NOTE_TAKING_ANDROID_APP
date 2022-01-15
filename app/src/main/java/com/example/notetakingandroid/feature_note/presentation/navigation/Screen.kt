package com.example.notetakingandroid.feature_note.presentation.navigation

sealed class Screen(
    val route: String
) {
    object NotesList: Screen("notes_list")
    object NoteAddEdit: Screen("add_edit_note")
    object NoteDetail: Screen("note_detail")
}