package com.hasandeniz.studentassistant.offlineCourses.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.RadioButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant.databinding.DaysBottomSheetDialogBinding
import com.hasandeniz.studentassistant.databinding.FragmentAddOfflineCourseBinding
import java.text.SimpleDateFormat
import java.util.*

object SharedFunctions {

    private fun showStartTimePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding) {
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

    private fun getStartTimeDatePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Show the time picker dialog
                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, selectedHour, selectedMinute ->
                        // Create the start time string in the ISO 8601 format
                        val startTime = String.format(
                            "%04d-%02d-%02dT%02d:%02d:00-07:00",
                            selectedYear, selectedMonth + 1, selectedDay, selectedHour, selectedMinute
                        )
                        // Use the start time string to create an event

                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.show()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()


    }

    private fun showFinishTimePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding) {
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

        bottomSheetBinding.saveButton.setOnClickListener {
            val selectedDay =
                bottomSheetBinding.dayGroup.findViewById<RadioButton>(bottomSheetBinding.dayGroup.checkedRadioButtonId)?.text.toString()
            binding.etDate.setText(selectedDay)
            daysBottomSheet.dismiss()
        }

        bottomSheetBinding.cancelButton.setOnClickListener {
            daysBottomSheet.dismiss()
        }

    }

    fun handleEditTextOnClicks(
        binding: FragmentAddOfflineCourseBinding, daysBottomSheet: BottomSheetDialog, context: Context
    ) {

        binding.etDate.setOnClickListener {
            daysBottomSheet.show()
        }

        binding.etStartTime.setOnClickListener {
            showStartTimePickerDialog(context, binding)
        }

        binding.etFinishTime.setOnClickListener {
            showFinishTimePickerDialog(context, binding)
        }


    }

}