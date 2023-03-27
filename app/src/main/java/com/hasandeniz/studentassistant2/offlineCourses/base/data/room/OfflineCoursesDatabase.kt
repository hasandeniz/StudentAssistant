package com.hasandeniz.studentassistant2.offlineCourses.base.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse

@Database(entities = [OfflineCourse::class], version = 4, exportSchema = false)
abstract class OfflineCoursesDatabase : RoomDatabase() {

    abstract fun offlineCourseDao(): OfflineCourseDao
}