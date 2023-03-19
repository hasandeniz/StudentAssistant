package com.hasandeniz.studentassistant2.offlineCourses.editCourse.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.BottomSheetDaysBinding
import com.hasandeniz.studentassistant2.databinding.FragmentAddOfflineCourseBinding
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.editCourse.viewModel.EditOfflineCourseViewModel
import com.hasandeniz.studentassistant2.offlineCourses.util.SharedFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditOfflineCourseFragment : Fragment() {
    private var _binding: FragmentAddOfflineCourseBinding? = null
    private val binding get() = _binding!!
    private lateinit var offlineCourse: OfflineCourse
    private val args: EditOfflineCourseFragmentArgs by navArgs()
    private var selectedColor = 0
    private val viewModel by viewModels<EditOfflineCourseViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOfflineCourseBinding.inflate(inflater, container, false)
        offlineCourse = args.offlineCourse
        selectedColor = offlineCourse.courseColor
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareUi()

        binding.btnCreateOfflineCourseAdd.setOnClickListener {
            updateOfflineCourse()
        }

        binding.btnCreateOfflineCourseBack.setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }

        val daysBottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetDaysBinding.inflate(layoutInflater)
        daysBottomSheet.setContentView(bottomSheetBinding.root)

        SharedFunctions.handleDaysButtonSheetButtons(binding, bottomSheetBinding, daysBottomSheet)

        SharedFunctions.handleEditTextOnClicks(binding, daysBottomSheet, requireContext())


        binding.ivPickCourseColor.setOnClickListener {
            MaterialColorPickerDialog.Builder(requireActivity())
                .setDefaultColor(selectedColor)
                .setColorRes(resources.getIntArray(R.array.course_colors))
                .setColorListener(object : ColorListener {
                    override fun onColorSelected(color: Int, colorHex: String) {
                        binding.ivPickCourseColor.setColorFilter(color)
                        selectedColor = color
                    }
                }).showBottomSheet(childFragmentManager)

        }


    }

    private fun updateOfflineCourse() {
        if (SharedFunctions.checkSaveState(binding)) {
            if (selectedColor == 0) selectedColor = R.color.light_backgrounds_closest_event_background
            val course = OfflineCourse(
                courseName = binding.etCourseName.text.toString(),
                teacherName = binding.etTeacherName.text.toString(),
                courseRoom = binding.etRoom.text.toString(),
                courseDay = binding.etDate.text.toString(),
                courseStartTime = binding.etStartTime.text.toString(),
                courseFinishTime = binding.etFinishTime.text.toString(),
                courseColor = selectedColor
            )
            course.uuid = offlineCourse.uuid
            viewModel.updateOfflineCourse(course)
            val action =
                EditOfflineCourseFragmentDirections.actionEditOfflineCourseFragmentToOfflineCourseDetailsFragment2(
                    course.uuid, course.courseName
                )
            val options = NavOptions.Builder().setPopUpTo(R.id.offlineCoursesFragment, false).build()
            findNavController().navigate(action, options)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun prepareUi() {
        selectedColor = offlineCourse.courseColor
        binding.etCourseName.setText(offlineCourse.courseName)
        binding.etTeacherName.setText(offlineCourse.teacherName)
        binding.etRoom.setText(offlineCourse.courseRoom)
        binding.etDate.setText(offlineCourse.courseDay)
        binding.etStartTime.setText(offlineCourse.courseStartTime)
        binding.etFinishTime.setText(offlineCourse.courseFinishTime)
        binding.ivPickCourseColor.setColorFilter(offlineCourse.courseColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}