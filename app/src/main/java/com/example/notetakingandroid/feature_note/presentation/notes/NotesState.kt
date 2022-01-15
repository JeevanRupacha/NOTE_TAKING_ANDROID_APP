package com.example.notetakingandroid.feature_note.presentation.notes

import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.util.NoteOrder
import com.example.notetakingandroid.feature_note.domain.util.OrderType

data class NotesState (
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
)