package com.hasandeniz.studentassistant.offlineCourses.addCourse.view

import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.hasandeniz.studentassistant.R
import com.hasandeniz.studentassistant.databinding.DaysBottomSheetDialogBinding
import com.hasandeniz.studentassistant.databinding.FragmentAddOfflineCourseBinding
import com.hasandeniz.studentassistant.offlineCourses.addCourse.viewModel.AddOfflineCourseViewModel
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import com.hasandeniz.studentassistant.offlineCourses.util.SharedFunctions
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

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
        val bottomSheetBinding = DaysBottomSheetDialogBinding.inflate(layoutInflater)
        daysBottomSheet.setContentView(bottomSheetBinding.root)

        SharedFunctions.handleDaysButtonSheetButtons(binding, bottomSheetBinding, daysBottomSheet)

        SharedFunctions.handleEditTextOnClicks(binding, daysBottomSheet, requireContext())


        binding.apply {
            btnPickCourseColor.setOnClickListener {
                MaterialColorPickerDialog.Builder(requireActivity()).setColorSwatch(ColorSwatch._300)
                    .setDefaultColor(R.color.dark_base_blue).setColorListener(object : ColorListener {
                        override fun onColorSelected(color: Int, colorHex: String) {
                            btnPickCourseColor.setColorFilter(color)
                            selectedColor = color
                        }
                    }).showBottomSheet(childFragmentManager)

            }
        }
    }


    private fun insertOfflineCourse() {
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