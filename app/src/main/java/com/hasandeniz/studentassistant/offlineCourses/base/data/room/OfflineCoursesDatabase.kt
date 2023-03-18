package com.hasandeniz.studentassistant.offlineCourses.base.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hasandeniz.studentassistant.grades.base.data.model.Grade
import com.hasandeniz.studentassistant.grades.base.data.room.GradesTypeConverter
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse

@Database(entities = [OfflineCourse::class], version = 1, exportSchema = false)
abstract class OfflineCoursesDatabase : RoomDatabase() {

    abstract fun offlineCourseDao(): OfflineCourseDao
}