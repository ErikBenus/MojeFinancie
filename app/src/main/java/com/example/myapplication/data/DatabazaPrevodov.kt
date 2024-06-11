package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.R

@Database(entities = [Prevod::class, Investment::class], version = 2, exportSchema = false)
abstract class DatabazaPrevodov : RoomDatabase() {

    abstract fun prevodDao(): PrevodDao
    abstract fun investmentDao(): InvestmentDao  // Pridanie nového DAO

    companion object {
        @Volatile
        private var Instance: DatabazaPrevodov? = null

        fun getDatabase(context: Context): DatabazaPrevodov {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DatabazaPrevodov::class.java,
                    context.getString(R.string.prevody_database)
                )
                    .fallbackToDestructiveMigration()  // Pridajte toto, ak chcete zničiť a znovu vytvoriť databázu pri zmene verzie
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Vytvorenie novej tabuľky
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `investments` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `ticker` TEXT NOT NULL,
                `startingAmount` REAL NOT NULL,
                `currentAmount` REAL NOT NULL,
                `type` TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}
