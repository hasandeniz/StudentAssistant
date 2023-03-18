package com.hasandeniz.studentassistant.grades.addGrade.viewModel

import androidx.lifecycle.LiveData
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
class AddGradeViewModel @Inject constructor(
    private val gradeRepository: GradeRepository,
    private val offlineCourseRepository: OfflineCourseRepository
) : ViewModel() {

    val offlineCourseLiveData: LiveData<List<OfflineCourse>> = offlineCourseRepository.getAllOfflineCourses()

    fun insertGrade(grade: Grade) {
        viewModelScope.launch(Dispatchers.IO) {
            gradeRepository.insertGrade(grade)
        }
    }

}