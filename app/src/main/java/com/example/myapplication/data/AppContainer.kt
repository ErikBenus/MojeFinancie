package com.example.myapplication.data

import android.content.Context



interface AppContainer {
    val prevodRepository: PrevodRepository
    val investmentRepository: InvestmentRepository
}



class AppDataContainer(private val context: Context) : AppContainer {
    override val prevodRepository: PrevodRepository by lazy {
        OfflinePrevodRepository(DatabazaPrevodov.getDatabase(context).prevodDao())
    }

    override val investmentRepository: InvestmentRepository by lazy {  // Add this block
        OfflineInvestmentRepository(DatabazaPrevodov.getDatabase(context).investmentDao())
    }
}

