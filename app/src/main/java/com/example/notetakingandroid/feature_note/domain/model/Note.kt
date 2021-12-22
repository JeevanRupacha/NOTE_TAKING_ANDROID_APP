package com.example.notetakingandroid.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notetakingandroid.ui.theme.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    @ColumnInfo(name ="title")
    val title:String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long,

    @ColumnInfo(name = "color")
    val color: Int? = null,
)
{
    companion object{
        val colors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
        val TABLE_NAME = "notes"
    }
}