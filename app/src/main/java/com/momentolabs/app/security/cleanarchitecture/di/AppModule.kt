package com.momentolabs.app.security.cleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.momentolabs.app.security.cleanarchitecture.db.NotesDao
import com.momentolabs.app.security.cleanarchitecture.db.NotesDatabase
import com.momentolabs.app.security.cleanarchitecture.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providesNotesDao(notesDatabase: NotesDatabase): NotesDao = notesDatabase.noteDao()


    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context): NotesDatabase = Room.databaseBuilder(context, NotesDatabase::class.java, "NotesDatabase").build()


    @Provides
    fun providesUserRepository(userDao: NotesDao) : NotesRepository = NotesRepository(userDao)
}