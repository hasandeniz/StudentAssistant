package com.hasandeniz.studentassistant2.grades.base.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.BottomSheetGradesBinding
import com.hasandeniz.studentassistant2.databinding.FragmentGradesBinding
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.grades.base.ui.adapter.GradeAdapter
import com.hasandeniz.studentassistant2.grades.base.ui.viewModel.GradesViewModel
import com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter.RecyclerViewEmptyStateObserver
import dagger.hilt.android.AndroidEntryPoint
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

@AndroidEntryPoint
class GradesFragment : Fragment(), GradeAdapter.ClickListener {

    private val viewModel by viewModels<GradesViewModel>()
    private var _binding: FragmentGradesBinding? = null
    private val binding get() = _binding!!
    private val sectionedAdapter = SectionedRecyclerViewAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGradesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gradesFloatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_gradesFragment_to_addGradeFragment)
        }

        binding.gradesRecyclerView.adapter = sectionedAdapter
        val emptyStateObserver =
            RecyclerViewEmptyStateObserver(binding.gradeEmptyState.root, binding.gradesRecyclerView)
        sectionedAdapter.registerAdapterDataObserver(emptyStateObserver)

        viewModel.gradeLiveData.observe(viewLifecycleOwner) { grades ->
            val groupedGrades = grades.groupBy { it.courseId }
            viewModel.offlineCourseLiveData.observe(viewLifecycleOwner) { offlineCourses ->
                sectionedAdapter.removeAllSections()
                for (course in offlineCourses) {
                    if (groupedGrades.containsKey(course.uuid)) {
                        sectionedAdapter.addSection(groupedGrades[course.uuid]?.let { it1 ->
                            GradeAdapter(
                                course.courseName, it1 as ArrayList<Grade>, this
                            )
                        })
                    }
                }
                sectionedAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun onItemRootViewClicked(adapter: GradeAdapter, grade: Grade, position: Int) {

        val bottomSheetBinding = BottomSheetGradesBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            tvGradeBottomSheetGradeType.text = grade.type
            tvGradeBottomSheetTitle.text = grade.courseName
            tvGradeDetailsGradeGrade.text = grade.grade.toString()
            tvGradeBottomSheetGradeWeight.text = "${grade.weight} %"
            decideGradeColor(this, grade)
        }


        val dialog = BottomSheetDialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()

        bottomSheetBinding.btnGradeBottomSheetEdit.setOnClickListener {
            val action = GradesFragmentDirections.editGrade(grade)
            Navigation.findNavController(binding.root).navigate(action)
            dialog.dismiss()
        }

        bottomSheetBinding.btnGradeBottomSheetDelete.setOnClickListener {
            viewModel.deleteGradeFromDatabase(grade)

            if (adapter.list.isEmpty()) {
                val sectionItemsTotal = adapter.sectionItemsTotal
                val sectionPosition = sectionedAdapter.getAdapterForSection(adapter).sectionPosition

                sectionedAdapter.removeSection(adapter)

                if (adapter.state == Section.State.LOADED) {
                    sectionedAdapter.notifyItemRangeRemoved(
                        sectionPosition, sectionItemsTotal
                    )
                }
            }

            dialog.dismiss()
        }
    }

    private fun decideGradeColor(binding: BottomSheetGradesBinding, grade: Grade) {
        binding.apply {
            val colorResId = when {
                grade.grade >= 80 -> R.color.light_grades_highest_grade
                grade.grade in 60..79 -> R.color.light_grades_second_highest_grade
                grade.grade in 50..59 -> R.color.light_grades_third_highest_grade
                else -> R.color.light_grades_low_grade
            }
            ivGradeDetailsGradesRvBackground.background.setTint(
                ContextCompat.getColor(requireContext(), colorResId)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}