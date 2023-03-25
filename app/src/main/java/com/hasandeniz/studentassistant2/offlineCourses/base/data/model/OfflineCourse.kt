package com.hasandeniz.studentassistant2.offlineCourses.base.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "offline_course_table")
data class OfflineCourse(

    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "Teacher Name")
    val teacherName: String,

    @ColumnInfo(name = "Day")
    val day: String,

    @ColumnInfo(name = "Start Time")
    val startTime: String,

    @ColumnInfo(name = "Finish Time")
    val finishTime: String,

    @ColumnInfo(name = "Location")
    val location: String,

    @ColumnInfo(name = "Color")
    val color: Int,

    @ColumnInfo(name = "Last Accessed")
    var lastAccessed: Long ,


): Serializable{
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
