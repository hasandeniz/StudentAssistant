package com.hasandeniz.studentassistant.offlineCourses.base.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.hasandeniz.studentassistant.R
import com.hasandeniz.studentassistant.databinding.FragmentOfflineCoursesBinding
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.base.ui.adapter.OfflineCoursesAdapter
import com.hasandeniz.studentassistant.offlineCourses.base.ui.adapter.RecyclerViewEmptyStateObserver
import com.hasandeniz.studentassistant.offlineCourses.base.ui.viewModel.OfflineCoursesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineCoursesFragment : Fragment(), OfflineCoursesAdapter.OnItemClickListener {

    private val viewModel by viewModels<OfflineCoursesViewModel>()
    private var _binding: FragmentOfflineCoursesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfflineCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OfflineCoursesAdapter(this)
        binding.apply {
            coursesRecyclerView.setHasFixedSize(true)
            coursesRecyclerView.adapter = adapter
        }

        val emptyStateObserver =
            RecyclerViewEmptyStateObserver(binding.offlineCourseEmptyState.root, binding.coursesRecyclerView)
        adapter.registerAdapterDataObserver(emptyStateObserver)

        viewModel.allOfflineCourses.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_offlineCoursesFragment_to_addOfflineCourseFragment)
        }


    }

    override fun onItemClick(offlineCourse: OfflineCourse) {
        val unWrappedDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.indicator_background)
        val wrappedDrawable = DrawableCompat.wrap(unWrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, offlineCourse.courseColor)
        val action = OfflineCoursesFragmentDirections.actionOfflineCoursesFragmentToOfflineCourseDetailsFragment(
            offlineCourse.uuid,
            offlineCourse.courseName
        )
        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}