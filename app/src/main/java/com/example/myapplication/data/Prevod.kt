package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "prevody")
data class Prevod(
@PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nazov: String,
    val hodnota: Double,
    val typ: TypPrevodu
)

enum class TypPrevodu {
    PRIJEM,
    VYDAJ
}