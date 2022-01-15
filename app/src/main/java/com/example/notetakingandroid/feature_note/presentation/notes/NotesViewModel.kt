package com.example.notetakingandroid.feature_note.presentation.notes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.use_case.NoteUseCases
import com.example.notetakingandroid.feature_note.domain.util.NoteOrder
import com.example.notetakingandroid.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

const val TAG = "NotesViewModel"

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
):ViewModel(){

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val _recentlyDeletedNote = mutableStateOf<Note?>(null)

    //To only initialize one concurrent job
    private var getNotesJob: Job? = null

    val loading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            getNotes(NoteOrder.Date(OrderType.Ascending))
            Log.d(TAG, "Init called ")
        }
    }

    fun onTriggerEvent(event: NotesEvent)
    {
        viewModelScope.launch {
            try {
                when (event) {
                    is NotesEvent.Order -> {

                        Log.d(TAG, "onTriggerEvent: orderType ${event.noteOrder.orderType}")
                        if (state.value.noteOrder::class == event.noteOrder::class &&
                            state.value.noteOrder.orderType == event.noteOrder.orderType
                        ) {
                            return@launch
                        }else{
                            getNotes(event.noteOrder)
                        }

                    }

                    is NotesEvent.RestoreNote -> {
                            noteUseCases.addNote(note = _recentlyDeletedNote.value!!)
                    }

                    is NotesEvent.ToggleOrderSection -> {
                        _state.value = state.value.copy(
                            isOrderSectionVisible = !state.value.isOrderSectionVisible
                        )
                    }

                    is NotesEvent.DeleteNote -> {
                        noteUseCases.getNote(id = event.id!!).onEach { dataState ->
                            loading.value = dataState.loading

                            Log.d(TAG, "getNote: ${dataState.data}")

                            _recentlyDeletedNote.value = dataState.data
                            noteUseCases.deleteNote.execute(event.id)

                            dataState.error?.let { err ->
                                Log.d(TAG, "getNote: $err")
                            }
                        }.launchIn(viewModelScope)
//                             getNote(event.id!!)
                            //TODO live data with flow is not working
                            //getNotes(state.value.noteOrder)
                        Log.d(TAG, "deleted data: ${_recentlyDeletedNote.value} ")
                        }
                    }
            }catch (e: Exception)
            {
                Log.d(TAG, "onTriggerEvent: ${e.cause}")

            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder)
    {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases
            .getNotes
            .invoke(noteOrder)
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { notes ->
                    Log.d(TAG, "getNotes: after order: $notes")
                    _state.value = state.value.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }

                dataState.error?.let{ err ->
                    Log.d(TAG, "getNotes: $err")
                    throw Exception("Error occured ")
                }
            }.launchIn(viewModelScope)
    }
}