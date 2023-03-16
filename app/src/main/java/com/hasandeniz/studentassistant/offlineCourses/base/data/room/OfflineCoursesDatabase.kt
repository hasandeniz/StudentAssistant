package com.hasandeniz.studentassistant.offlineCourses.base.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse

@Database(entities = [OfflineCourse::class], version = 1, exportSchema = false)
abstract class OfflineCoursesDatabase : RoomDatabase() {

    abstract fun offlineCourseDao(): OfflineCourseDao
}