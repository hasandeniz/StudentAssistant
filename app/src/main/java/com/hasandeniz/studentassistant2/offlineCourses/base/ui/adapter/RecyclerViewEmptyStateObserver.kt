package com.hasandeniz.studentassistant2.offlineCourses.base.ui.adapter

import android.content.ContentValues.TAG
import android.util.Log
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
            Log.d(TAG, "checkIfEmpty: emptyState")
            recyclerView.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            Log.d(TAG, "checkIfEmpty: not emptyState")
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}
