package com.hasandeniz.studentassistant.grades.base.ui.viewModel

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
class GradesViewModel @Inject constructor(
    private val gradeRepository: GradeRepository,
    private val offlineCourseRepository: OfflineCourseRepository
) : ViewModel() {

    val offlineCourseLiveData: LiveData<List<OfflineCourse>>
    val gradeLiveData: LiveData<List<Grade>>

    init {
        offlineCourseLiveData = offlineCourseRepository.getAllOfflineCourses()
        gradeLiveData = gradeRepository.getAllGrades()
    }

    fun deleteGradeFromDatabase(grade: Grade) {
        viewModelScope.launch(Dispatchers.IO) {
            gradeRepository.deleteGrade(grade)
        }
    }


}