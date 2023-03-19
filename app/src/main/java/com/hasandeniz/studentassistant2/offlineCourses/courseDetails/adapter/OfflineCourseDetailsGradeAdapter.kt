package com.hasandeniz.studentassistant2.offlineCourses.courseDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.ItemOfflineCourseDetailsGradeBinding
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade

class OfflineCourseDetailsGradeAdapter :
    ListAdapter<Grade, OfflineCourseDetailsGradeAdapter.OfflineCourseDetailViewHolder>(GradeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineCourseDetailViewHolder {
        val binding = ItemOfflineCourseDetailsGradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfflineCourseDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfflineCourseDetailViewHolder, position: Int) {
        val currentGrade = getItem(position)

        if (currentGrade != null) {
            holder.bind(currentGrade)
        }
    }

    inner class OfflineCourseDetailViewHolder(private val binding: ItemOfflineCourseDetailsGradeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(grade: Grade) {
            binding.apply {
                tvCourseDetailsGradeGrade.text = grade.grade.toString()
                tvCourseDetailsGradeType.text = grade.type
                decideGradeColor(binding, grade)
            }
        }
    }

    private fun decideGradeColor(binding: ItemOfflineCourseDetailsGradeBinding, grade: Grade){
        binding.apply {
            val gradeColor = ivCourseDetailsGradesRvBackground
            if (grade.grade >= 80)
                gradeColor.background.setTint(ContextCompat.getColor(root.context, R.color.light_grades_highest_grade))
            else if (grade.grade in 60..79)
                gradeColor.background.setTint(ContextCompat.getColor(root.context,R.color.light_grades_second_highest_grade))
            else if (grade.grade in 50..59)
                gradeColor.background.setTint(ContextCompat.getColor(root.context,R.color.light_grades_third_highest_grade))
            else
                gradeColor.background.setTint(ContextCompat.getColor(root.context,R.color.light_grades_low_grade))
        }

    }

    class GradeDiffCallback : DiffUtil.ItemCallback<Grade>() {
        override fun areItemsTheSame(oldItem: Grade, newItem: Grade): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: Grade, newItem: Grade): Boolean {
            return oldItem == newItem
        }
    }
}



