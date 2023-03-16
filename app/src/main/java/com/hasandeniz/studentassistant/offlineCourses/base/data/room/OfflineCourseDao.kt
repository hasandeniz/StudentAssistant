package com.hasandeniz.studentassistant.offlineCourses.base.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse

@Dao
interface OfflineCourseDao {

    @Insert
    suspend fun insertOfflineCourse(offlineCourse: OfflineCourse)

    @Delete
    suspend fun deleteOfflineCourse(offlineCourse: OfflineCourse)

    @Update
    suspend fun updateOfflineCourse(offlineCourse: OfflineCourse)

    @Query("SELECT * FROM offline_course_table")
    fun getAllOfflineCourses(): LiveData<List<OfflineCourse>>

    @Query("SELECT * FROM offline_course_table WHERE uuid = :uuid")
    suspend fun getOfflineCourseByUuid(uuid: Int): OfflineCourse
}