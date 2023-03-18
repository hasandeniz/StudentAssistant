package com.hasandeniz.studentassistant.offlineCourses.util

import android.app.TimePickerDialog
import android.content.Context
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant.R
import com.hasandeniz.studentassistant.databinding.BottomSheetGradesBinding
import com.hasandeniz.studentassistant.databinding.DaysBottomSheetDialogBinding
import com.hasandeniz.studentassistant.databinding.FragmentAddOfflineCourseBinding
import java.text.SimpleDateFormat
import java.util.*

object SharedFunctions {

    fun showStartTimePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context, { _, hourOfDay, minuteOfHour ->
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

    fun showFinishTimePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context, { _, hourOfDay, minuteOfHour ->
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

    fun checkSaveState(binding: FragmentAddOfflineCourseBinding): Boolean {
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

    fun handleDaysButtonSheetButtons(
        binding: FragmentAddOfflineCourseBinding,
        bottomSheetBinding: DaysBottomSheetDialogBinding,
        daysBottomSheet: BottomSheetDialog
    ) {
        bottomSheetBinding.apply {
            saveButton.setOnClickListener {
                val selectedDay =
                    bottomSheetBinding.dayGroup.findViewById<RadioButton>(bottomSheetBinding.dayGroup.checkedRadioButtonId)?.text.toString()
                binding.etDate.setText(selectedDay)
                daysBottomSheet.dismiss()
            }

            cancelButton.setOnClickListener {
                daysBottomSheet.dismiss()
            }
        }
    }

    fun handleEditTextOnClicks(
        binding: FragmentAddOfflineCourseBinding,
        daysBottomSheet: BottomSheetDialog,
        context: Context
    ) {
        binding.apply {
            etDate.setOnClickListener {
                daysBottomSheet.show()
            }

            etStartTime.setOnClickListener {
                showStartTimePickerDialog(context, binding)
            }

            etFinishTime.setOnClickListener {
                showFinishTimePickerDialog(context, binding)
            }

        }
    }

}