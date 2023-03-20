package com.hasandeniz.studentassistant2.grades.util

import com.hasandeniz.studentassistant2.databinding.FragmentAddGradeBinding

object GradeUtil {

    fun checkAllFields(binding: FragmentAddGradeBinding): Boolean {
        var allFieldsFilled = true

        for (editText in listOf(
            binding.etGrade,
            binding.etPickCourse,
            binding.etGradeType,
            binding.etWeight
        )) {
            if (editText.text.isBlank()) {
                allFieldsFilled = false
            }
        }
        return allFieldsFilled
    }
}