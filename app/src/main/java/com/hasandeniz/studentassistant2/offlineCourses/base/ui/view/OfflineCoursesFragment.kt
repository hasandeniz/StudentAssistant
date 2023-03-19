package com.hasandeniz.studentassistant2.offlineCourses.base.ui.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.BottomSheetDeleteCourseBinding
import com.hasandeniz.studentassistant2.databinding.FragmentOfflineCoursesBinding
import com.hasandeniz.studentassistant2.databinding.ItemOfflineCourseBinding
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter.OfflineCoursesAdapter
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter.RecyclerViewEmptyStateObserver
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.viewModel.OfflineCoursesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineCoursesFragment : Fragment(), OfflineCoursesAdapter.OnItemClickListener, OfflineCoursesAdapter.OnButtonMoreClickListener {

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

        val adapter = OfflineCoursesAdapter(this,this)
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
                    val daysBottomSheet = BottomSheetDialog(requireContext())
                    val bottomSheetBinding = BottomSheetDeleteCourseBinding.inflate(layoutInflater)
                    daysBottomSheet.setContentView(bottomSheetBinding.root)
                    bottomSheetBinding.btnCancel.setOnClickListener {
                        daysBottomSheet.dismiss()
                    }
                    bottomSheetBinding.btnDelete.setOnClickListener {
                        val sharedPref = requireActivity().getPreferences(MODE_PRIVATE)
                        if (sharedPref!!.contains(offlineCourse.uuid.toString())) {
                            sharedPref.edit {
                                remove(offlineCourse.uuid.toString())
                            }

                        }
                        daysBottomSheet.dismiss()
                        viewModel.deleteCourse(offlineCourse.uuid)
                        daysBottomSheet.dismiss()
                    }
                    daysBottomSheet.show()

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