package com.hasandeniz.studentassistant.offlineCourses.base.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.studentassistant.databinding.ItemOfflineCourseBinding
import com.hasandeniz.studentassistant.offlineCourses.base.data.model.OfflineCourse

class OfflineCoursesAdapter (private val listener: OnItemClickListener):
    ListAdapter<OfflineCourse, OfflineCoursesAdapter.OfflineCourseViewHolder>(OfflineCourseCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineCourseViewHolder {
        val binding = ItemOfflineCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfflineCourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfflineCourseViewHolder, position: Int) {
        val currentOfflineCourse = getItem(position)

        if (currentOfflineCourse != null) {
            holder.bind(currentOfflineCourse)
        }
    }

    inner class OfflineCourseViewHolder(private val binding: ItemOfflineCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val offlineCourse = getItem(position)
                    if (offlineCourse != null) {
                        listener.onItemClick(offlineCourse)
                    }
                }
            }
        }

        fun bind(offlineCourse: OfflineCourse) {
            val combinedDate = "${offlineCourse.courseDay}, ${offlineCourse.courseStartTime}"
            binding.apply {
                tvCourseName.text = offlineCourse.courseName
                tvCourseDate.text = combinedDate
                tvCourseRoom.text = offlineCourse.courseRoom
                tvTeacherName.text = offlineCourse.teacherName
                ivIndicatorColor.setBackgroundColor(offlineCourse.courseColor)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(offlineCourse: OfflineCourse)
    }

    class OfflineCourseCallback : DiffUtil.ItemCallback<OfflineCourse>() {
        override fun areItemsTheSame(oldItem: OfflineCourse, newItem: OfflineCourse): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: OfflineCourse, newItem: OfflineCourse): Boolean {
            return oldItem == newItem
        }
    }
}