package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(investment: Investment)

    @Update
    suspend fun update(investment: Investment)

    @Delete
    suspend fun delete(investment: Investment)

    @Query("SELECT * FROM investments WHERE id = :id")
    fun getInvestment(id: Int): Flow<Investment?>

    @Query("SELECT * FROM investments ORDER BY name ASC")
    fun getAllInvestments(): Flow<List<Investment>>

    @Query("SELECT * FROM investments WHERE type = :type")
    fun getAllInvestmentsByType(type: InvestmentType): Flow<List<Investment>>
}
