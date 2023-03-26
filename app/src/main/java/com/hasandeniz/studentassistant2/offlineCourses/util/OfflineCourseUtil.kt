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

    /**
     * Shows a time picker dialog for selecting the start or finish time of a course.
     *
     * @param context the context in which to show the dialog
     * @param binding the binding of the fragment to update with the selected time
     * @param isStartTime true if the start time should be updated, false if the finish time should be updated
     */
    private fun showTimePickerDialog(context: Context, binding: FragmentAddOfflineCourseBinding, isStartTime: Boolean) {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        val timePickerDialog = TimePickerDialog(
            context, { _, hourOfDay, minuteOfHour ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minuteOfHour)
                }

                val selectedTime = timeFormat.format(selectedCalendar.time)

                if (isStartTime) {
                    binding.etStartTime.setText(selectedTime)
                } else {
                    binding.etFinishTime.setText(selectedTime)
                }
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )

        timePickerDialog.show()
    }

    /**
     * Checks if all required fields in the fragment's layout have been filled.
     *
     * @param binding the binding of the fragment's layout
     * @return true if all required fields are filled, false otherwise
     */
    fun checkSaveState(binding: FragmentAddOfflineCourseBinding): Boolean {
        return isAllFieldsFilled(binding)
    }

    /**
     * Checks if all required EditText fields in the fragment's layout have non-blank text.
     *
     * @param binding the binding of the fragment's layout
     * @return true if all required EditText fields have non-blank text, false otherwise
     */
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

    /**
     * Sets click listeners on the buttons in the days selection bottom sheet.
     *
     * @param binding the binding of the fragment's layout
     * @param bottomSheetBinding the binding of the bottom sheet layout
     * @param daysBottomSheet the bottom sheet dialog to which the buttons belong
     */
    fun setDaysButtonSheetButtonClickListeners(
        binding: FragmentAddOfflineCourseBinding,
        bottomSheetBinding: BottomSheetDaysBinding,
        daysBottomSheet: BottomSheetDialog
    ) {
        bottomSheetBinding.apply {
            listOf(
                btnMonday,
                btnTuesday,
                btnWednesday,
                btnThursday,
                btnFriday,
                btnSaturday,
                btnSunday
            ).forEach { button ->
                button.setOnClickListener {
                    setEditTextDate(button, binding)
                    daysBottomSheet.dismiss()
                }
            }
        }
    }

    /**
     * Sets the date EditText to the selected day.
     *
     * @param button the button that was clicked to select the day
     * @param binding the binding of the fragment's layout
     */
    private fun setEditTextDate(button: MaterialButton, binding: FragmentAddOfflineCourseBinding) {
        val selectedDay = button.text.toString()
        binding.etDate.setText(selectedDay)

    }

    /**
     * Attaches click listeners to the date and time EditText views in a fragment's layout.
     *
     * @param binding the binding of the fragment's layout
     * @param daysBottomSheet the bottom sheet dialog to show when the date EditText is clicked
     * @param context the context in which to show the time picker dialog
     */
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

    /**
     * Removes the shared preference entry for a given offline course from the activity's preferences.
     *
     * @param offlineCourse the offline course to remove from shared preferences
     * @param activity the activity in which the shared preferences are stored
     */
    fun handleSharedPrefCleaning(offlineCourse: OfflineCourse, activity: FragmentActivity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref?.contains(offlineCourse.uuid.toString()) == true) {
            sharedPref.edit { remove(offlineCourse.uuid.toString()) }
        }
    }


}