package com.momentolabs.app.security.cleanarchitecture.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.momentolabs.app.security.cleanarchitecture.models.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun noteDao() : NotesDao
}