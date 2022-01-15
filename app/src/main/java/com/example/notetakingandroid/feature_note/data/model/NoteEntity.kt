package com.example.notetakingandroid.feature_note.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notetakingandroid.feature_note.data.model.NoteEntity.Companion.TABLE_NAME
import com.example.notetakingandroid.ui.theme.*

@Entity(tableName = TABLE_NAME)
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int? = null,

    @ColumnInfo(name = "title")
    val title: String ,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name ="timeStamp")
    val timeStamp: Long,

    @ColumnInfo(name = "color")
    val color: Int? = null,
) {
    companion object{
        const val TABLE_NAME = "note"
    }
}