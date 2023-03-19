package com.hasandeniz.studentassistant2.offlineCourses.base.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "offline_course_table")
data class OfflineCourse(

    @ColumnInfo(name = "Name")
    val courseName: String,

    @ColumnInfo(name = "Teacher Name")
    val teacherName: String,

    @ColumnInfo(name = "Day")
    val courseDay: String,

    @ColumnInfo(name = "Start Time")
    val courseStartTime: String,

    @ColumnInfo(name = "Finish Time")
    val courseFinishTime: String,

    @ColumnInfo(name = "Room")
    val courseRoom: String,

    @ColumnInfo(name = "Color")
    val courseColor: Int


): Serializable{
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
