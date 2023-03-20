package com.hasandeniz.studentassistant2.offlineCourses.addCourse.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.BottomSheetDaysBinding
import com.hasandeniz.studentassistant2.databinding.FragmentAddOfflineCourseBinding
import com.hasandeniz.studentassistant2.offlineCourses.addCourse.viewModel.AddOfflineCourseViewModel
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant2.offlineCourses.util.OfflineCourseUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOfflineCourseFragment : Fragment() {

    private val viewModel by viewModels<AddOfflineCourseViewModel>()

    private var _binding: FragmentAddOfflineCourseBinding? = null
    private val binding get() = _binding!!

    private var selectedColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOfflineCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnCreateOfflineCourseAdd.setOnClickListener {
            insertOfflineCourse()
        }

        binding.btnCreateOfflineCourseBack.setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }


        val daysBottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetDaysBinding.inflate(layoutInflater)
        daysBottomSheet.setContentView(bottomSheetBinding.root)

        OfflineCourseUtil.handleDaysButtonSheetButtons(binding, bottomSheetBinding, daysBottomSheet)

        OfflineCourseUtil.handleEditTextOnClicks(binding, daysBottomSheet, requireContext())

        selectedColor = resources.getIntArray(R.array.course_colors)[0]
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


    private fun insertOfflineCourse() {
        if (OfflineCourseUtil.checkSaveState(binding)) {
            if (selectedColor == 0) selectedColor = R.color.course_color_1
            val course = OfflineCourse(
                courseName = binding.etCourseName.text.toString(),
                teacherName = binding.etTeacherName.text.toString(),
                courseRoom = binding.etRoom.text.toString(),
                courseDay = binding.etDate.text.toString(),
                courseStartTime = binding.etStartTime.text.toString(),
                courseFinishTime = binding.etFinishTime.text.toString(),
                courseColor = selectedColor
            )
            viewModel.insertOfflineCourse(course)
            val navController = findNavController()
            navController.navigateUp()
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}