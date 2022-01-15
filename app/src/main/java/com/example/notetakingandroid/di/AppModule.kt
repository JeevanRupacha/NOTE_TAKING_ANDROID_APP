package com.example.notetakingandroid.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.notetakingandroid.BaseApplication
import com.example.notetakingandroid.feature_note.data.data_source.NoteDao
import com.example.notetakingandroid.feature_note.data.data_source.NoteDatabase
import com.example.notetakingandroid.feature_note.data.model.NoteEntityMapper
import com.example.notetakingandroid.feature_note.data.repository.NoteRepository_imp
import com.example.notetakingandroid.feature_note.domain.repository.NoteRepository
import com.example.notetakingandroid.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.processedrootsentinel.ProcessedRootSentinel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBaseApplication(@ApplicationContext app: Context) : BaseApplication
    {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideNoteEntityMapper(): NoteEntityMapper
    {
        return NoteEntityMapper()
    }

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase
    {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao
    {
        return db.noteDao()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(
        mapper : NoteEntityMapper,
        noteDao: NoteDao
    ) : NoteRepository
    {
        return NoteRepository_imp(dao = noteDao, mapper = mapper)
    }

    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases
    {
        return NoteUseCases(
            deleteNote = DeleteNote(repository),
            getNotes = GetNotes(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
        )
    }
}