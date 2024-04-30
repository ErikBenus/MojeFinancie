package com.example.myapplication.data

import android.content.Context


// Interface defining the dependency container
interface AppContainer {
    val prevodRepository: PrevodRepository
}

// Implementation of AppContainer
class AppDataContainer(private val context: Context) : AppContainer {
    override val prevodRepository: PrevodRepository by lazy {
        OfflinePrevodRepository(DatabazaPrevodov.getDatabase(context).prevodDao())
    }
}
