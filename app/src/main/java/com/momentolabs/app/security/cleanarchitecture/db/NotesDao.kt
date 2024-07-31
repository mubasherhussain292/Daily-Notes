package com.momentolabs.app.security.cleanarchitecture.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.momentolabs.app.security.cleanarchitecture.models.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)


    @Query("Select * From Notes")
    fun getAllNotes(): Flow<List<Notes>>


    @Query("DELETE FROM notes WHERE id IN (:idList)")
    fun deleteByIdList(idList: List<Int>)


}