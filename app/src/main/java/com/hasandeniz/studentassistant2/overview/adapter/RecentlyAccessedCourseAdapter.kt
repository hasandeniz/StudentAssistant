package com.hasandeniz.studentassistant2.overview.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.studentassistant2.databinding.ItemRecentlyAccessedCourseBinding
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse

class RecentlyAccessedCourseAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<OfflineCourse, RecentlyAccessedCourseAdapter.RecentlyAccessedCourseViewHolder>(
        RecentlyAccessedCoursesCallback()
    ) {

    inner class RecentlyAccessedCourseViewHolder(private val binding: ItemRecentlyAccessedCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val offlineCourse = getItem(position)
                    if (offlineCourse != null) {
                        itemClickListener.onItemClick(offlineCourse)
                    }
                }
            }
        }

        fun bind(offlineCourse: OfflineCourse) {
            binding.apply {
                tvRecentlyAccessedItemTitle.text = offlineCourse.courseName
                tvRecentlyAccessedItemTeacherName.text = offlineCourse.teacherName
                tvRecentlyAccessedItemRoom.text = offlineCourse.courseRoom
                val courseDate = offlineCourse.courseDay + ", " + offlineCourse.courseStartTime
                tvRecentlyAccessedItemDate.text = courseDate
                ivRecentlyAccessedCourseIndicator.setBackgroundColor(offlineCourse.courseColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyAccessedCourseViewHolder {
        val binding = ItemRecentlyAccessedCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyAccessedCourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyAccessedCourseViewHolder, position: Int) {
        val currentCourse = getItem(position)

        if (currentCourse != null)
            holder.bind(currentCourse)
    }

    interface OnItemClickListener {
        fun onItemClick(offlineCourse: OfflineCourse)
    }

    class RecentlyAccessedCoursesCallback : DiffUtil.ItemCallback<OfflineCourse>() {
        override fun areItemsTheSame(oldItem: OfflineCourse, newItem: OfflineCourse): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: OfflineCourse, newItem: OfflineCourse): Boolean {
            return oldItem == newItem
        }
    }
}
