package com.hasandeniz.studentassistant2.grades.base.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.ItemGradeBinding
import com.hasandeniz.studentassistant2.databinding.SectionHeaderBinding
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.grades.base.ui.view.GradesFragmentDirections
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class GradeAdapter(
    private val title: String,
    val list: ArrayList<Grade>,
    private val clickListener: ClickListener
) :
    Section(
        SectionParameters.builder()
            .itemResourceId(R.layout.item_grade)
            .headerResourceId(R.layout.section_header)
            .build()
    ) {

    private var selectedCourseId = 0
    private var selectedCourseName = ""
    private var selectedCourseColor = Color.BLUE

    inner class GradeViewHolder(private val binding: ItemGradeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(grade: Grade, position: Int) {
            selectedCourseId = grade.courseId
            selectedCourseColor = grade.courseColor
            selectedCourseName = grade.courseName
            binding.tvGradeCourseName.text = grade.courseName
            binding.tvGrade.text = grade.grade.toString()
            binding.tvGradeType.text = grade.type
            decideGradeColor(binding, grade)
            binding.root.setOnClickListener {
                clickListener.onItemRootViewClicked(this@GradeAdapter, grade, position)
            }
        }
    }

    inner class GradeHeaderViewHolder(private val binding: SectionHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnMore.setOnClickListener {
                val action = GradesFragmentDirections.actionGradesFragmentToOfflineCourseDetailsFragment(
                    selectedCourseId,
                    selectedCourseName,
                )
                Navigation.findNavController(it).navigate(action)
            }
        }

        fun bind() {
            binding.tvTitle.text = title
        }
    }

    override fun getContentItemsTotal(): Int {
        return list.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        val binding = ItemGradeBinding.inflate(LayoutInflater.from(view.context), view as ViewGroup,false)
        return GradeViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as GradeViewHolder
        val grade = list[position]
        itemHolder.bind(grade, position)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        val binding = SectionHeaderBinding.inflate(LayoutInflater.from(view.context), view as ViewGroup,false)
        return GradeHeaderViewHolder(binding)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder = holder as GradeHeaderViewHolder
        headerHolder.bind()
    }

    interface ClickListener {
        fun onItemRootViewClicked(adapter: GradeAdapter, grade: Grade, position: Int)
    }

    private fun decideGradeColor(binding: ItemGradeBinding, grade: Grade) {
        val colorRes = when (grade.grade) {
            in 80..Int.MAX_VALUE -> R.color.light_grades_highest_grade
            in 60..79 -> R.color.light_grades_second_highest_grade
            in 50..59 -> R.color.light_grades_third_highest_grade
            else -> R.color.light_grades_low_grade
        }
        binding.ivRoundGradeColor.background.setTint(ContextCompat.getColor(binding.root.context, colorRes))
    }
}

