package com.example.keepnotes.di

import android.app.Application
import androidx.room.Room
import com.example.keepnotes.data.dao.NoteDatabase
import com.example.keepnotes.data.repository.NoteRepositoryImpl
import com.example.keepnotes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
    @InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase) : NoteRepository{
        return NoteRepositoryImpl(dao = db.noteDao)
    }
}