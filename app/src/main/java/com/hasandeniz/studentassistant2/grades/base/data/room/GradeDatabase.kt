package com.hasandeniz.studentassistant2.grades.base.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade

@Database(entities = [Grade::class], version = 1, exportSchema = false)
abstract class GradeDatabase : RoomDatabase() {
    abstract fun gradeDao(): GradeDao
}