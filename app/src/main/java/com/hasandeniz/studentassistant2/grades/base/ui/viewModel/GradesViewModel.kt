package com.hasandeniz.studentassistant2.grades.base.ui.viewModel

import androidx.lifecycle.LiveData
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
class GradesViewModel @Inject constructor(
    private val gradeRepository: GradeRepository,
    offlineCourseRepository: OfflineCourseRepository
) : ViewModel() {

    val offlineCourseLiveData: LiveData<List<OfflineCourse>> = offlineCourseRepository.getAllOfflineCourses()
    val gradeLiveData: LiveData<List<Grade>> = gradeRepository.getAllGrades()

    fun deleteGradeFromDatabase(grade: Grade) {
        viewModelScope.launch(Dispatchers.IO) {
            gradeRepository.deleteGrade(grade)
        }
    }


}