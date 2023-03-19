package com.hasandeniz.studentassistant2.grades.base.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade

@Dao
interface GradeDao {

    @Insert
    suspend fun insertGrade(grade: Grade)

    @Delete
    suspend fun deleteGrade(grade: Grade)

    @Update
    suspend fun updateGrade(grade: Grade)

    @Query("SELECT * FROM grade_table")
    fun getAllGrades() : LiveData<List<Grade>>

    @Query("SELECT * FROM grade_table WHERE uuid = :uuid")
    suspend fun getGradeByUuid(uuid: Int) : Grade

    @Query("SELECT * FROM grade_table WHERE relatedCourse = :sectionId")
    suspend fun getGradesOfSection(sectionId: Int) : List<Grade>

    @Query("DELETE FROM grade_table WHERE relatedCourse = :courseId")
    suspend fun deleteGradesOfCourse(courseId: Int)

}