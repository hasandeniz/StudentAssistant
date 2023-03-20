package com.hasandeniz.studentassistant2.grades.addGrade.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hasandeniz.studentassistant2.databinding.FragmentAddGradeBinding
import com.hasandeniz.studentassistant2.grades.addGrade.viewModel.AddGradeViewModel
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.grades.util.GradeUtil
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGradeFragment : Fragment() {

    private val viewModel by viewModels<AddGradeViewModel>()
    private var _binding: FragmentAddGradeBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedCourse: OfflineCourse
    private lateinit var selectedCourseName: String
    private lateinit var selectedGradeType: String
    private lateinit var selectedCourseId: String

    private var selectedCourseColor = 0
    private var addGradeCourses = arrayListOf<String>()
    private var coursesList = listOf<OfflineCourse>()
    private var selectedGradeTypeIndex: Int = 0
    private val selectableGradeType = arrayOf("Written", "Verbal")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGradeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.offlineCourseLiveData.observe(viewLifecycleOwner) { offlineCourses ->
            offlineCourses?.let {
                for (course in offlineCourses) {
                    addGradeCourses.add(course.courseName)
                }
                coursesList = offlineCourses
            }
        }

        binding.apply {
            etPickCourse.setOnClickListener {
                showRadioGradeCourses()
            }

            etGradeType.setOnClickListener {
                showRadioGradeTypes()
            }
            btnAddGradeBack.setOnClickListener {
                val navController = findNavController()
                navController.navigateUp()
            }
            btnAddGradeAdd.setOnClickListener {
                if (GradeUtil.checkAllFields(binding)) {
                    val gradeValue = etGrade.text.toString().toInt()
                    val gradeWeight = etWeight.text.toString().toInt()
                    val gradeType = etGradeType.text.toString()
                    val grade = Grade(
                        selectedCourseName,
                        gradeValue,
                        selectedCourseId.toInt(),
                        gradeWeight,
                        gradeType,
                        selectedCourseColor
                    )

                    viewModel.insertGrade(grade)

                    val navController = findNavController()
                    navController.navigateUp()
                } else {
                    Toast.makeText(activity, "Fill all the blank input fields!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showRadioGradeCourses() {
        if (addGradeCourses.isNotEmpty()) {
            var selectedCourseIndex = 0
            selectedCourseName = addGradeCourses[selectedCourseIndex]
            val coursesArray = arrayOfNulls<String>(addGradeCourses.size)
            addGradeCourses.toArray(coursesArray)
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Pick a course")
                .setSingleChoiceItems(coursesArray, selectedCourseIndex) { _, which ->
                    selectedCourseIndex = which
                    selectedCourseName = addGradeCourses[which]
                }
                .setPositiveButton("Ok") { _, _ ->
                    selectedCourse = coursesList[selectedCourseIndex]
                    selectedCourseId = coursesList[selectedCourseIndex].uuid.toString()
                    binding.etPickCourse.setText(selectedCourseName)
                    selectedCourseColor = selectedCourse.courseColor
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else
            Toast.makeText(context, "Please add at least 1 course", Toast.LENGTH_SHORT).show()

    }

    private fun showRadioGradeTypes() {
        selectedGradeType = selectableGradeType[selectedGradeTypeIndex]
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Choose a grade type")
            .setSingleChoiceItems(selectableGradeType, selectedGradeTypeIndex) { _, which ->
                selectedGradeTypeIndex = which
                selectedGradeType = selectableGradeType[which]
            }
            .setPositiveButton("Ok") { _, _ ->
                binding.etGradeType.setText(selectedGradeType)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}