package com.hasandeniz.studentassistant.offlineCourses.courseDetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasandeniz.studentassistant.grades.base.data.model.Grade
import com.hasandeniz.studentassistant.grades.base.data.repository.GradeRepository
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.base.data.repository.OfflineCourseRepository
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
    val offlineCourseLiveData : LiveData<OfflineCourse>
            get() = _offlineCourseLiveData
    val gradesLiveData : LiveData<List<Grade>>
            get() = _gradesLiveData

    //Todo: Recently Accessed Courses kısımlarını sonradan ekle

    fun deleteCourse(uuid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val offlineCourse = offlineCourseRepository.getOfflineCourseByUuid(uuid)
            offlineCourseRepository.deleteOfflineCourse(offlineCourse)
            gradeRepository.deleteGradesOfCourse(uuid)
        }
    }

    fun getOfflineCourse(uuid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val offlineCourse = offlineCourseRepository.getOfflineCourseByUuid(uuid)
            _offlineCourseLiveData.postValue(offlineCourse)
        }
    }

    fun getOfflineCourseList(courseUuid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val grades = gradeRepository.getGradesOfSection(courseUuid)
            _gradesLiveData.postValue(grades)
        }
    }
}