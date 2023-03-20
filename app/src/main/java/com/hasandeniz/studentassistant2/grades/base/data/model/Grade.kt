package com.hasandeniz.studentassistant2.grades.base.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "grade_table")
data class Grade(

    @ColumnInfo(name = "courseName")
    val courseName:String,

    @ColumnInfo(name = "gradeValue")
    val grade:Int,

    @ColumnInfo(name = "relatedCourse")
    val courseId:Int,

    @ColumnInfo(name = "gradeWeight")
    val weight:Int,

    @ColumnInfo(name = "gradeType")
    val type:String,

    ): Serializable{
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
