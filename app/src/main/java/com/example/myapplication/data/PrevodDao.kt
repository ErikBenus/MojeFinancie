package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PrevodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(prevod: Prevod)

    @Update
    suspend fun update(prevod: Prevod)

    @Delete
    suspend fun delete(prevod: Prevod)

    @Query("SELECT * FROM prevody WHERE id = :id")
    fun getPrevod(id: Int): Flow<Prevod?>  // Flow s možnosťou null hodnoty pre prípad, že prevod neexistuje

    @Query("SELECT * FROM prevody ORDER BY nazov ASC")
    fun getAllPrevody(): Flow<List<Prevod>>

    @Query("SELECT * FROM prevody WHERE typ = :typ")
    fun getAllPrevodyByType(typ: TypPrevodu): Flow<List<Prevod>>
}