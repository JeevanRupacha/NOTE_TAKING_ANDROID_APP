package com.example.notetakingandroid.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notetakingandroid.feature_note.data.data_source.NoteDao
import com.example.notetakingandroid.feature_note.data.model.NoteEntity
import com.example.notetakingandroid.feature_note.domain.model.Note

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}