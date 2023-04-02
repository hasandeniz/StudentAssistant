package com.hasandeniz.studentassistant2.overview.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(offlineCourseRepository: OfflineCourseRepository): ViewModel() {
    val allOfflineCourses: LiveData<List<OfflineCourse>> = offlineCourseRepository.getAllOfflineCourses()

    fun setCalendar(time:String,day:String): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY,getHour(time))
        calendar.set(Calendar.MINUTE,getMinute(time))
        calendar.set(Calendar.DAY_OF_WEEK,getDay(day))
        return calendar
    }

    private fun getDay(day:String):Int{
        return when(day){
            "Monday"-> 2
            "Tuesday"-> 3
            "Wednesday"-> 4
            "Thursday"-> 5
            "Friday"-> 6
            "Saturday"-> 7
            "Sunday"-> 1
            else->{
                -1
            }
        }
    }

    private fun getHour(time:String):Int{
        return time.split(":").toTypedArray()[0].toInt()
    }

    private fun getMinute(time:String):Int{
        return time.split(":").toTypedArray()[1].toInt()
    }
}