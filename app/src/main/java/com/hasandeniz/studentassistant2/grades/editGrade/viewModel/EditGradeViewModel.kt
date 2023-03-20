package com.hasandeniz.studentassistant2.grades.editGrade.viewModel

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
class EditGradeViewModel @Inject constructor(
    private val repository: GradeRepository,
    offlineCourseRepository: OfflineCourseRepository
) : ViewModel() {

    val offlineCourseLiveData: LiveData<List<OfflineCourse>> = offlineCourseRepository.getAllOfflineCourses()

    fun updateGrade(grade: Grade) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGrade(grade)
        }
    }
}