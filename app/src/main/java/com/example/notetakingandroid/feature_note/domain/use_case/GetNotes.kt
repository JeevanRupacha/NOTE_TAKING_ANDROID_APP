package com.example.notetakingandroid.feature_note.domain.use_case

import android.util.Log
import androidx.room.Index
import com.example.notetakingandroid.feature_note.data.data_source.DataState
import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository
import com.example.notetakingandroid.feature_note.domain.util.NoteOrder
import com.example.notetakingandroid.feature_note.domain.util.OrderType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
const val TAG = "GetNotes"
class GetNotes(
    private val repository: NoteRepository
) {
    suspend fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<DataState<List<Note>>> = flow {

        try {
            emit(DataState.loading())

            //delay(2000)
            //simulate the loading


            val sortedNotes = repository.getAllNotes().map { notes ->

                Log.d(TAG, "invoke: The Note $notes")
                when (noteOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (noteOrder) {
                            is NoteOrder.Title -> notes.sortedBy { it.title }
                            is NoteOrder.Date -> {
                                Log.d(TAG, "invoke: timestamp sorting ..")
                                notes.sortedBy {
                                    it.timeStamp
                                }
                            }
                            is NoteOrder.Color -> notes.sortedBy { it.color }
                        }
                    }

                    is OrderType.Descending -> {
                        when (noteOrder) {
                            is NoteOrder.Title -> notes.sortedByDescending { it.title }
                            is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                            is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        }
                    }
                }
            }


//                emit(DataState.success(data = sortedNotes))
            emit(DataState(listOf(Note(uid = 2,title ="sdgsd","Sdfsdf",3434, 35))))
            } catch (e: Exception)
            {
                Log.d(TAG, "invoke: ${e.cause}")
                emit(DataState.failure(message = e.message ?: "Unknown error "))
            }
    }
}