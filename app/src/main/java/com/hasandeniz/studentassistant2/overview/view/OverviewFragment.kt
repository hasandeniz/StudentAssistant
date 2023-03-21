package com.hasandeniz.studentassistant2.overview.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.FragmentOverviewBinding
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter.RecyclerViewEmptyStateObserver
import com.hasandeniz.studentassistant2.overview.adapter.RecentlyAccessedCourseAdapter
import com.hasandeniz.studentassistant2.overview.util.RecentlyAccessedCourses
import com.hasandeniz.studentassistant2.overview.viewModel.OverViewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(), RecentlyAccessedCourseAdapter.OnItemClickListener {

    private val viewModel by viewModels<OverViewViewModel>()
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private var addButtonClicked = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAddButtonOnclickEvents()

        binding.recentlyAccessedEmptyState.btnNoRecentlyAccessedCourseGetStarted.setOnClickListener {
            val action = OverviewFragmentDirections.overviewToAddOfflineCourse()
            findNavController().navigate(action)
        }

        binding.btnRecentlyAccessedCoursesMore.setOnClickListener {
            val action = OverviewFragmentDirections.overviewToOfflineCourse()
            val options = NavOptions.Builder().setPopUpTo(R.id.overviewFragment, true).build()
            findNavController().navigate(action, options)
        }

        val adapter = RecentlyAccessedCourseAdapter(this)
        binding.rvRecentlyAccessed.setHasFixedSize(true)
        binding.rvRecentlyAccessed.adapter = adapter

        val emptyStateObserverRecentlyAccessedCourses =
            RecyclerViewEmptyStateObserver(binding.recentlyAccessedEmptyState.root, binding.rvRecentlyAccessed)
        adapter.registerAdapterDataObserver(emptyStateObserverRecentlyAccessedCourses)
        val recentlyAccessedCourses = RecentlyAccessedCourses.getAllRecentlyAccessedCourses(requireActivity())

        adapter.submitList(recentlyAccessedCourses)

    }

    private fun onAddButtonClicked() {
        setAddButtonVisibility(addButtonClicked)
        setAddButtonAnimation(addButtonClicked)
        addButtonClicked = !addButtonClicked
    }

    private fun setAddButtonOnclickEvents() {
        binding.addFloatingActionButton.setOnClickListener {
            onAddButtonClicked()
        }
        binding.courseFloatingActionButton.setOnClickListener {
            val action = OverviewFragmentDirections.overviewToAddOfflineCourse()
            findNavController().navigate(action)
            addButtonClicked = !addButtonClicked
        }
        binding.gradesFloatingActionButton.setOnClickListener {
            val action = OverviewFragmentDirections.overviewToAddGrade()
            findNavController().navigate(action)
            addButtonClicked = !addButtonClicked
        }

    }

    private fun setAddButtonAnimation(clicked: Boolean) {
        val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.rotate_open_anim) }
        val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.rotate_close_anim) }
        val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.from_bottom_anim) }
        val toBottom: Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.to_bottom_anim) }
        if (!clicked) {
            binding.courseFloatingActionButton.startAnimation(fromBottom)
            binding.gradesFloatingActionButton.startAnimation(fromBottom)
            binding.addFloatingActionButton.startAnimation(rotateOpen)
        } else {
            binding.courseFloatingActionButton.startAnimation(toBottom)
            binding.gradesFloatingActionButton.startAnimation(toBottom)
            binding.addFloatingActionButton.startAnimation(rotateClose)
        }
    }

    private fun setAddButtonVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.courseFloatingActionButton.visibility = View.VISIBLE
            binding.gradesFloatingActionButton.visibility = View.VISIBLE
        } else {
            binding.courseFloatingActionButton.visibility = View.INVISIBLE
            binding.gradesFloatingActionButton.visibility = View.INVISIBLE
        }
    }

    override fun onItemClick(offlineCourse: OfflineCourse) {
        Toast.makeText(requireContext(), offlineCourse.courseName, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}