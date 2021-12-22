package com.example.notetakingandroid.feature_note.domain.util

interface DomainMapper<T,DomainModel> {
    fun mapToDomain(model: T): DomainModel
    fun mapFromDomain(model: DomainModel): T
}