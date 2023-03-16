package com.hasandeniz.studentassistant.offlineCourses.addCourse.ui.view

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.hasandeniz.studentassistant.R
import com.hasandeniz.studentassistant.databinding.DaysBottomSheetDialogBinding
import com.hasandeniz.studentassistant.databinding.FragmentAddOfflineCourseBinding
import com.hasandeniz.studentassistant.offlineCourses.addCourse.ui.viewModel.AddOfflineCourseViewModel
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddOfflineCourseFragment : Fragment() {

    private val viewModel by viewModels<AddOfflineCourseViewModel>()

    private var _binding: FragmentAddOfflineCourseBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOfflineCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_save, menu)
                val saveButton = menu.findItem(R.id.action_save)
                saveButton.setActionView(R.layout.menu_item_save_button)
                saveButton.actionView?.findViewById<MaterialButton>(R.id.menu_item_button)?.setOnClickListener {
                    insertOfflineCourse()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                navController.navigateUp()
                return true
            }
        }, viewLifecycleOwner)


        /*binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }*/

        bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = DaysBottomSheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.saveButton.setOnClickListener {
            val selectedDay =
                bottomSheetBinding.dayGroup.findViewById<RadioButton>(bottomSheetBinding.dayGroup.checkedRadioButtonId)?.text.toString()
            binding.etDate.setText(selectedDay)
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        binding.etDate.setOnClickListener {
            bottomSheetDialog.show()
        }

        binding.etStartTime.setOnClickListener {
            showStartTimePickerDialog()
        }

        binding.etFinishTime.setOnClickListener {
            showFinishTimePickerDialog()
        }

        /*binding.btnAdd.setOnClickListener {
            insertOfflineCourse()
        }*/

    }

    private fun checkSaveState(): Boolean {
        var allFieldsFilled = true

        for (editText in listOf(
            binding.etDate,
            binding.etStartTime,
            binding.etFinishTime,
            binding.etCourseName,
            binding.etTeacherName,
            binding.etRoom
        )) {
            if (editText.text.isBlank()) {
                allFieldsFilled = false
            }
        }
        return allFieldsFilled
    }

    private fun insertOfflineCourse() {
        if (checkSaveState()) {
            val course = OfflineCourse(
                courseName = binding.etCourseName.text.toString(),
                teacherName = binding.etTeacherName.text.toString(),
                courseRoom = binding.etRoom.text.toString(),
                courseDay = binding.etDate.text.toString(),
                courseStartTime = binding.etStartTime.text.toString(),
                courseFinishTime = binding.etFinishTime.text.toString(),
                courseColor = R.color.dark_base_blue
            )
            viewModel.insertOfflineCourse(course)
            val navController = findNavController()
            navController.navigateUp()
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showStartTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(), { _, hourOfDay, minuteOfHour ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minuteOfHour)

                val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val selectedStartingTime = sdf.format(selectedCalendar.time)
                binding.etStartTime.setText(selectedStartingTime)

            }, hour, minute, false
        )

        timePickerDialog.show()
    }

    private fun showFinishTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(), { _, hourOfDay, minuteOfHour ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minuteOfHour)

                val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val selectedFinishingTime = sdf.format(selectedCalendar.time)
                binding.etFinishTime.setText(selectedFinishingTime)

            }, hour, minute, false
        )

        timePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}