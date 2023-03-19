package com.hasandeniz.studentassistant2.grades.base.data.repository

import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.grades.base.data.room.GradeDao
import javax.inject.Inject

class GradeRepository @Inject constructor(
    private val gradeDao: GradeDao
) {
    suspend fun insertGrade(grade: Grade) = gradeDao.insertGrade(grade)

    suspend fun deleteGrade(grade: Grade) = gradeDao.deleteGrade(grade)

    suspend fun updateGrade(grade: Grade) = gradeDao.updateGrade(grade)

    fun getAllGrades() = gradeDao.getAllGrades()

    suspend fun getGradeByUuid(uuid: Int) = gradeDao.getGradeByUuid(uuid)

    suspend fun deleteGradesOfCourse(courseUuid: Int) = gradeDao.deleteGradesOfCourse(courseUuid)

    suspend fun getGradesOfSection(courseUuid: Int): List<Grade> = gradeDao.getGradesOfSection(courseUuid)

}



