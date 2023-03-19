package com.hasandeniz.studentassistant2.offlineCourses.base.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasandeniz.studentassistant2.grades.base.data.repository.GradeRepository
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineCoursesViewModel @Inject constructor(
    private val offlineCourseRepository: OfflineCourseRepository,
    private val gradeRepository: GradeRepository
) : ViewModel() {

    val allOfflineCourses: LiveData<List<OfflineCourse>> = offlineCourseRepository.getAllOfflineCourses()

    fun deleteCourse(uuid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val offlineCourse = offlineCourseRepository.getOfflineCourseByUuid(uuid)
            offlineCourseRepository.deleteOfflineCourse(offlineCourse)
            gradeRepository.deleteGradesOfCourse(uuid)
        }
    }

}