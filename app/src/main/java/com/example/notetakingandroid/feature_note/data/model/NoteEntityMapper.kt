package com.example.notetakingandroid.feature_note.data.model

import com.example.notetakingandroid.feature_note.domain.model.Note
import com.example.notetakingandroid.feature_note.domain.util.DomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteEntityMapper: DomainMapper<NoteEntity, Note> {
    override fun mapToDomain(model: NoteEntity): Note {
        return (
                Note(
                    uid = model.uid,
                    title = model.title,
                    content = model.content,
                    color = model.color,
                    timeStamp = model.timeStamp,
                    )
                )
    }

    override fun mapFromDomain(model: Note): NoteEntity {
        return (
                NoteEntity(
                    uid = model.uid,
                    title = model.title,
                    content = model.content,
                    color = model.color,
                    timeStamp = model.timeStamp,
                    )
                )
    }

    fun convertListToString(listVal: List<String>): String
    {
        return listVal.reduce{acc, s -> "$acc , $s"}
    }

    fun convertStringToList(stringVal: String): List<String>
    {
        return stringVal.split(",")
    }

    fun mapToEntityList(initial: List<Note>): List<NoteEntity>
    {
        return initial.map{mapFromDomain(it)}
    }

    fun mapFromEntityList(initial: List<NoteEntity>): List<Note>
    {
        return initial.map { mapToDomain(it)}
    }
}