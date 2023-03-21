package com.hasandeniz.studentassistant2.offlineCourses.base.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.BottomSheetDeleteBinding
import com.hasandeniz.studentassistant2.databinding.FragmentOfflineCoursesBinding
import com.hasandeniz.studentassistant2.databinding.ItemOfflineCourseBinding
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter.OfflineCoursesAdapter
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter.RecyclerViewEmptyStateObserver
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.viewModel.OfflineCoursesViewModel
import com.hasandeniz.studentassistant2.offlineCourses.util.OfflineCourseUtil
import com.hasandeniz.studentassistant2.overview.util.RecentlyAccessedCourses
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineCoursesFragment : Fragment(), OfflineCoursesAdapter.OnItemClickListener,
    OfflineCoursesAdapter.OnButtonMoreClickListener {

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

        val adapter = OfflineCoursesAdapter(this, this)

        binding.coursesRecyclerView.setHasFixedSize(true)
        binding.coursesRecyclerView.adapter = adapter


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
        val action = OfflineCoursesFragmentDirections.actionOfflineCoursesFragmentToOfflineCourseDetailsFragment(
            offlineCourse.uuid,
            offlineCourse.courseName
        )
        findNavController().navigate(action)

    }

    override fun onButtonMoreClick(offlineCourse: OfflineCourse, binding: ItemOfflineCourseBinding) {
        val popup = PopupMenu(requireContext(), binding.ivMore)
        popup.inflate(R.menu.menu_course_details)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    val navController = findNavController()
                    val action = OfflineCoursesFragmentDirections
                        .actionOfflineCoursesFragmentToEditOfflineCourseFragment(offlineCourse)
                    navController.navigate(action)

                }

                R.id.menu_delete -> {
                    popup.dismiss()
                    val deleteBottomSheet = BottomSheetDialog(requireContext())
                    val bottomSheetBinding = BottomSheetDeleteBinding.inflate(layoutInflater)
                    deleteBottomSheet.setContentView(bottomSheetBinding.root)
                    bottomSheetBinding.tvTitle.text = getString(R.string.delete_course)
                    bottomSheetBinding.tvMessage.text = getString(R.string.delete_course_message)
                    bottomSheetBinding.btnCancel.setOnClickListener {
                        deleteBottomSheet.dismiss()
                    }
                    bottomSheetBinding.btnDelete.setOnClickListener {
                        OfflineCourseUtil.handleSharedPrefCleaning(offlineCourse, requireActivity())
                        RecentlyAccessedCourses.deleteRecentlyAccessedCourse(offlineCourse, requireActivity())
                        viewModel.deleteCourse(offlineCourse.uuid)
                        deleteBottomSheet.dismiss()
                    }
                    deleteBottomSheet.show()

                }

            }

            true
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}