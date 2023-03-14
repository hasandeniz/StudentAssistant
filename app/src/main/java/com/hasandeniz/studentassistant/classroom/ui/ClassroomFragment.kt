package com.hasandeniz.studentassistant.classroom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hasandeniz.studentassistant.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassroomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classroom, container, false)
    }

}