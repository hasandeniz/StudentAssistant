package com.hasandeniz.studentassistant.courses.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OfflineCourse(

    @ColumnInfo(name = "courseName")
    val courseName: String,

    @ColumnInfo(name = "teacherName")
    val teacherName: String,

    @ColumnInfo(name = "courseDay")
    val courseDay: String,

    @ColumnInfo(name = "courseStartTime")
    val courseStartTime: String,

    @ColumnInfo(name = "courseFinishTime")
    val courseFinishTime: String,

    @ColumnInfo(name = "courseRoom")
    val courseRoom: String,

    @ColumnInfo(name = "courseColor")
    val courseColor: Int


){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
