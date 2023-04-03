package com.hasandeniz.studentassistant2.overview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.studentassistant2.databinding.ItemClosestEventBinding

class UpcomingEventAdapter :
    ListAdapter<String, UpcomingEventAdapter.ViewHolder>(MyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClosestEventBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customEvent = getItem(position)
        holder.bind(customEvent)
    }

    class ViewHolder(private val binding: ItemClosestEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(customEvent: String) {
            // bind other views here if necessary
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}