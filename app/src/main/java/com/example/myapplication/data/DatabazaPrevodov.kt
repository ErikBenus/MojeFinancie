package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.R

@Database(entities = [Prevod::class], version = 1, exportSchema = false)
abstract class DatabazaPrevodov : RoomDatabase() {

    abstract fun prevodDao(): PrevodDao  // Aktualizované rozhranie Dao

    companion object {
        @Volatile
        private var Instance: DatabazaPrevodov? = null

        fun getDatabase(context: Context): DatabazaPrevodov {
            // Ak inštancia nie je null, vráť ju, inak vytvor novú inštanciu databázy.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DatabazaPrevodov::class.java,
                    context.getString(R.string.prevody_database))
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
