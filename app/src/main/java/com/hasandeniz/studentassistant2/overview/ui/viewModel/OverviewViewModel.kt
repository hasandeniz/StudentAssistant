package com.hasandeniz.studentassistant2.overview.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.data.repository.OfflineCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(offlineCourseRepository: OfflineCourseRepository): ViewModel() {
    val allOfflineCourses: LiveData<List<OfflineCourse>> = offlineCourseRepository.getAllOfflineCourses()
}