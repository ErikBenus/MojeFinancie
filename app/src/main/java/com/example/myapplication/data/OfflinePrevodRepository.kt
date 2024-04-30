package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

class OfflinePrevodRepository(private val prevodDao: PrevodDao) : PrevodRepository {
    override fun getPrevodStream(id: Int): Flow<Prevod?> = prevodDao.getPrevod(id)
    override fun getAllPrevodyStream(): Flow<List<Prevod>> = prevodDao.getAllPrevody()
    override fun getAllPrevodyByTypeStream(typ: TypPrevodu): Flow<List<Prevod>> = prevodDao.getAllPrevodyByType(typ)
    override suspend fun insertPrevod(prevod: Prevod) = prevodDao.insert(prevod)
    override suspend fun deletePrevod(prevod: Prevod) = prevodDao.delete(prevod)
    override suspend fun updatePrevod(prevod: Prevod) = prevodDao.update(prevod)
}