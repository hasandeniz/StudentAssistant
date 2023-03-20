package com.hasandeniz.studentassistant2.grades.editGrade.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hasandeniz.studentassistant2.databinding.FragmentAddGradeBinding
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.grades.editGrade.viewModel.EditGradeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditGradeFragment : Fragment() {

    private val viewModel by viewModels<EditGradeViewModel>()
    private var _binding: FragmentAddGradeBinding? = null
    private val binding get() = _binding!!
    private val args: EditGradeFragmentArgs by navArgs()
    private lateinit var grade: Grade

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGradeBinding.inflate(inflater, container, false)
        grade = args.grade
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkAllFields(): Boolean {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}