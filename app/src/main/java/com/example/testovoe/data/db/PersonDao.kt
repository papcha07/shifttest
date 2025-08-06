package com.example.testovoe.data.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonEntity>)
    @Query("SELECT * FROM person_table")
    suspend fun getAllPersons(): List<PersonEntity>
    @Query("DELETE FROM person_table")
    suspend fun clearAllPersons()

}