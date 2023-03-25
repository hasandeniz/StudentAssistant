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

    private fun showTimePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding, isStartTime: Boolean) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context, { _, hourOfDay, minuteOfHour ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minuteOfHour)

                val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val selectedTime = sdf.format(selectedCalendar.time)

                if (isStartTime) {
                    binding.etStartTime.setText(selectedTime)
                } else {
                    binding.etFinishTime.setText(selectedTime)
                }
            }, hour, minute, false
        )

        timePickerDialog.show()
    }

    fun checkSaveState(binding: FragmentAddOfflineCourseBinding): Boolean {
        return isAllFieldsFilled(binding)
    }

    private fun isAllFieldsFilled(binding: FragmentAddOfflineCourseBinding): Boolean {
        return listOf(
            binding.etDate,
            binding.etStartTime,
            binding.etFinishTime,
            binding.etCourseName,
            binding.etTeacherName,
            binding.etRoom
        ).all { editText ->
            editText.text.isNotBlank()
        }
    }

    fun setDaysButtonSheetButtonClickListeners(
        binding: FragmentAddOfflineCourseBinding,
        bottomSheetBinding: BottomSheetDaysBinding,
        daysBottomSheet: BottomSheetDialog
    ) {
        bottomSheetBinding.apply {
            listOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday).forEach { button ->
                button.setOnClickListener {
                    setEditTextDate(button, binding)
                    daysBottomSheet.dismiss()
                }
            }
        }
    }

    private fun setEditTextDate(button: MaterialButton, binding: FragmentAddOfflineCourseBinding) {
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
            showTimePickerDialog(context, binding, isStartTime = true)
        }

        binding.etFinishTime.setOnClickListener {
            showTimePickerDialog(context, binding, isStartTime = false)
        }
    }

    fun handleSharedPrefCleaning(offlineCourse: OfflineCourse, activity: FragmentActivity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref?.contains(offlineCourse.uuid.toString()) == true) {
            sharedPref.edit { remove(offlineCourse.uuid.toString()) }
        }
    }


}