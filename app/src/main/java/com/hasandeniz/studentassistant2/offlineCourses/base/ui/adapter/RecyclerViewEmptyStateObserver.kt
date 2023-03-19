package com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEmptyStateObserver constructor(private val emptyState: View, private val recyclerView: RecyclerView) :
    RecyclerView.AdapterDataObserver() {

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (recyclerView.adapter?.itemCount == 0) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}
