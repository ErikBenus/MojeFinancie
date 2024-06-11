package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "investments")
data class Investment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val ticker: String,
    val startingAmount: Double,
    val currentAmount: Double,
    val type: InvestmentType
)

enum class InvestmentType {
    Stock,
    Crypto
}
