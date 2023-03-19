package com.hasandeniz.studentassistant.offlineCourses.editCourse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditOfflineCourseViewModel @Inject constructor(private val offlineCourseRepository: OfflineCourseRepository) :
    ViewModel() {


    fun updateOfflineCourse(offlineCourse: OfflineCourse) {
        viewModelScope.launch {
            offlineCourseRepository.updateOfflineCourse(offlineCourse)
        }
    }
}