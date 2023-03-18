package com.hasandeniz.studentassistant.grades.base.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hasandeniz.studentassistant.grades.base.data.model.Grade

class GradesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): ArrayList<Grade> {
        val listType = object : TypeToken<ArrayList<Grade>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: ArrayList<Grade>): String {
        return gson.toJson(list)
    }
}
