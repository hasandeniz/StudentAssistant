package com.hasandeniz.studentassistant.offlineCourses.base.data.repository

import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.base.data.room.OfflineCourseDao
import javax.inject.Inject

class OfflineCourseRepository @Inject constructor(
    private val offlineCourseDao: OfflineCourseDao
){
    suspend fun insertOfflineCourse(offlineCourse: OfflineCourse) = offlineCourseDao.insertOfflineCourse(offlineCourse)
    suspend fun deleteOfflineCourse(offlineCourse: OfflineCourse) = offlineCourseDao.deleteOfflineCourse(offlineCourse)
    suspend fun updateOfflineCourse(offlineCourse: OfflineCourse) = offlineCourseDao.updateOfflineCourse(offlineCourse)
    fun getAllOfflineCourses() = offlineCourseDao.getAllOfflineCourses()
    suspend fun getOfflineCourseByUuid(uuid: Int) = offlineCourseDao.getOfflineCourseByUuid(uuid)
}