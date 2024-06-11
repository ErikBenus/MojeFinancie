package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

interface InvestmentRepository {
    fun getInvestmentStream(id: Int): Flow<Investment?>
    fun getAllInvestmentsStream(): Flow<List<Investment>>
    fun getAllInvestmentsByTypeStream(type: InvestmentType): Flow<List<Investment>>
    suspend fun insertInvestment(investment: Investment)
    suspend fun deleteInvestment(investment: Investment)
    suspend fun updateInvestment(investment: Investment)
}
