package com.example.notetakingandroid.feature_note.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notetakingandroid.ui.theme.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val uid: Int?,
    val title:String,
    val content: String,
    val timeStamp: Long,
    val color: Int? = null,
) : Parcelable
{
    companion object{
        val colors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}