package com.example.notetakingandroid.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingandroid.feature_note.data.model.NoteEntity
import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.use_case.NoteUseCases
import com.example.notetakingandroid.feature_note.domain.util.InvalidNoteException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AddEditNoteViewModel "

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter the title... "
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter the content..."
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.colors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentNoteId = mutableStateOf<Int?>(null)

    val loading = mutableStateOf(false)

    private var getNoteJob: Job? = null

    init {
        savedStateHandle.get<Int>("noteID")?.let{ noteID ->

            Log.d(TAG, "note id : $noteID")
            if(noteID != -1)
            {
                viewModelScope.launch {
                    getNote(noteID)
                }
            }
        }
    }

    fun onTriggerEvent(event: AddEditNoteEvent)
    {
        viewModelScope.launch {
            when(event)
            {
                is AddEditNoteEvent.EnteredTitle ->{
                    _noteTitle.value = noteTitle.value.copy(
                        text = event.value
                    )
                }

                is AddEditNoteEvent.EnteredContent -> {
                    _noteContent.value = noteContent.value.copy(
                        text = event.value
                    )
                }

                is AddEditNoteEvent.ChangeTitleFocus -> {
                    _noteTitle.value = noteTitle.value.copy(
                        isVisibleHint = !event.focusState.isFocused &&
                                noteTitle.value.text.isBlank()
                    )
                }

                is AddEditNoteEvent.ChangeContentFocus -> {
                    _noteContent.value = noteContent.value.copy(
                        isVisibleHint = !event.focusState.isFocused &&
                                noteContent.value.text.isBlank()
                    )
                }

                is AddEditNoteEvent.ChangeColor -> {
                    _noteColor.value = event.color
                }

                is AddEditNoteEvent.SaveNote -> {

                    val note =  Note(
                        uid = currentNoteId.value,
                        title = noteTitle.value.text,
                        content = noteContent.value.text,
                        color = noteColor.value,
                        timeStamp = System.currentTimeMillis()
                    )

                    viewModelScope.launch {
                        try{
                            noteUseCases.addNote.invoke(note)
                            //Event like only once execute when even rotate the screen
                            _eventFlow.emit(UiEvent.SaveNote)
                        }catch (e: InvalidNoteException)
                        {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: "Couldn't save note "
                                )
                            )
                        }
                    }
                }
            }
        }

    }


    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }

    private fun getNote(noteID: Int)
    {
        getNoteJob?.cancel()

        noteUseCases.getNote(id = noteID).onEach { dataState ->
            loading.value = dataState.loading

            currentNoteId.value = dataState.data?.uid

            _noteTitle.value = noteTitle.value.copy(
                text = dataState.data?.title?: "",
                isVisibleHint = false
            )

            _noteContent.value = noteContent.value.copy(
                text = dataState.data?.content?:"",
                isVisibleHint = false
            )

            _noteColor.value = dataState.data?.color!!


            dataState.error?.let { err ->
                Log.d(TAG, "getNote: $err")
            }
        }.launchIn(viewModelScope)
    }

}