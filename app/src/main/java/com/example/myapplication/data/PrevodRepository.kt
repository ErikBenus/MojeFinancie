package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

interface PrevodRepository {
    fun getPrevodStream(id: Int): Flow<Prevod?> // Flow with nullable value for cases when the transaction does not exist
    fun getAllPrevodyStream(): Flow<List<Prevod>>
    fun getAllPrevodyByTypeStream(typ: TypPrevodu): Flow<List<Prevod>>
    suspend fun insertPrevod(prevod: Prevod)
    suspend fun deletePrevod(prevod: Prevod)
    suspend fun updatePrevod(prevod: Prevod)
}