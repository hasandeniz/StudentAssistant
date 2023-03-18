package com.hasandeniz.studentassistant.grades.base.data.model

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

    @ColumnInfo(name = "courseColor")
    val courseColor:Int = Color.BLUE,

    ){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
