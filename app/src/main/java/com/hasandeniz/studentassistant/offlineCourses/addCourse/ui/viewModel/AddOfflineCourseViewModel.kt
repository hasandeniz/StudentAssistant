package com.hasandeniz.studentassistant.offlineCourses.addCourse.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOfflineCourseViewModel @Inject constructor(private val repository: OfflineCourseRepository) : ViewModel(){

    fun insertOfflineCourse(offlineCourse: OfflineCourse){
        viewModelScope.launch {
            repository.insertOfflineCourse(offlineCourse)
        }
    }
}