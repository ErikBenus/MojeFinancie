package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

class OfflineInvestmentRepository(private val investmentDao: InvestmentDao) : InvestmentRepository {
    override fun getInvestmentStream(id: Int): Flow<Investment?> = investmentDao.getInvestment(id)
    override fun getAllInvestmentsStream(): Flow<List<Investment>> = investmentDao.getAllInvestments()
    override fun getAllInvestmentsByTypeStream(type: InvestmentType): Flow<List<Investment>> = investmentDao.getAllInvestmentsByType(type)
    override suspend fun insertInvestment(investment: Investment) = investmentDao.insert(investment)
    override suspend fun deleteInvestment(investment: Investment) = investmentDao.delete(investment)
    override suspend fun updateInvestment(investment: Investment) = investmentDao.update(investment)
}
