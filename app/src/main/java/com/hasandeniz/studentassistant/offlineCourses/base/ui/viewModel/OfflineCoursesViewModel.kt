package com.hasandeniz.studentassistant.offlineCourses.base.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfflineCoursesViewModel @Inject constructor(repository: OfflineCourseRepository): ViewModel()  {

    val allOfflineCourses : LiveData<List<OfflineCourse>>
    init {
        repository.getAllOfflineCourses()
        allOfflineCourses = repository.getAllOfflineCourses()
    }

}