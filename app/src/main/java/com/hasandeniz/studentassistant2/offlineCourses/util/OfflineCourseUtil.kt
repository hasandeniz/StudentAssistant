package com.hasandeniz.studentassistant2.offlineCourses.util

import android.app.TimePickerDialog
import android.content.Context
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.hasandeniz.studentassistant2.databinding.BottomSheetDaysBinding
import com.hasandeniz.studentassistant2.databinding.FragmentAddOfflineCourseBinding
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse
import java.text.SimpleDateFormat
import java.util.*

object OfflineCourseUtil {

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
        bottomSheetBinding: BottomSheetDaysBinding,
        daysBottomSheet: BottomSheetDialog
    ) {
        bottomSheetBinding.apply {
            val buttonArray =
                listOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday)
            for (button in buttonArray) {
                button.setOnClickListener {
                    setEditTextText(button, binding)
                    daysBottomSheet.dismiss()
                }
            }
        }
    }

    private fun setEditTextText(button: MaterialButton, binding: FragmentAddOfflineCourseBinding) {
        val selectedDay = button.text.toString()
        binding.etDate.setText(selectedDay)

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

    fun handleSharedPrefCleaning(offlineCourse: OfflineCourse, activity: FragmentActivity){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref!!.contains(offlineCourse.uuid.toString())) {
            sharedPref.edit {
                remove(offlineCourse.uuid.toString())
            }
        }
    }


}