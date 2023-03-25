package com.hasandeniz.studentassistant2.offlineCourses.courseDetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.grades.base.data.repository.GradeRepository
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineCourseDetailsViewModel @Inject constructor(
    private val gradeRepository: GradeRepository,
    private val offlineCourseRepository: OfflineCourseRepository
) : ViewModel() {

    private val _offlineCourseLiveData = MutableLiveData<OfflineCourse>()
    private val _gradesLiveData = MutableLiveData<List<Grade>>()
    val offlineCourseLiveData: LiveData<OfflineCourse> get() = _offlineCourseLiveData
    val gradesLiveData: LiveData<List<Grade>> get() = _gradesLiveData


    fun deleteOfflineCourse(uuid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val offlineCourse = offlineCourseRepository.getOfflineCourseByUuid(uuid)
            offlineCourseRepository.deleteOfflineCourse(offlineCourse)
            gradeRepository.deleteGradesOfCourse(uuid)
        }
    }

    fun getOfflineCourse(uuid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val offlineCourse = offlineCourseRepository.getOfflineCourseByUuid(uuid)
            offlineCourse.lastAccessed = System.currentTimeMillis()
            offlineCourseRepository.updateOfflineCourse(offlineCourse)
            _offlineCourseLiveData.postValue(offlineCourse)
        }
    }

    fun getOfflineCourseGrades(courseUuid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val grades = gradeRepository.getGradesOfSection(courseUuid)
            _gradesLiveData.postValue(grades)
        }
    }
}