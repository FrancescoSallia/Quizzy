package com.example.quizzy.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return list.joinToString(",") // z.B. "1,2,3"
    }

    @TypeConverter
    fun toIntList(data: String): List<Int> {
        return if (data.isEmpty()) emptyList()
        else data.split(",").map { it.toInt() }
    }
}