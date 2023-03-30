package com.hasandeniz.studentassistant2.overview.model

import java.util.*

data class CustomEvent (
    val id: Long,
    val title: String,
    val name: String,
    val remaining: Int,
    val endTime: Calendar,
    val color: Int
)