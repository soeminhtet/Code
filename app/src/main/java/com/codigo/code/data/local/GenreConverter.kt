package com.codigo.code.data.local

import androidx.room.TypeConverter

class GenreConverter {

    private val separator = ","

    @TypeConverter
    fun convertListToInt(list: List<Int>): String {
        val stringBuilder = StringBuilder()
        for (item in list) {
            stringBuilder.append(item).append(separator)
        }

        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<Int> {
        return string.split(separator).map { it.toInt() }
    }
}