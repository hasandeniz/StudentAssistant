package com.hasandeniz.studentassistant2.overview.util

import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hasandeniz.studentassistant2.offlineCourses.base.data.model.OfflineCourse


object RecentlyAccessedCourses {
    private val MAX_SIZE = 5

    fun addRecentlyAccessedCourse(offlineCourse: OfflineCourse, activity: FragmentActivity) {
        val sharedPreferences = activity.getPreferences(FragmentActivity.MODE_PRIVATE)
        if (sharedPreferences.contains("recently_accessed_courses")) {

            val gson = Gson()
            val json = sharedPreferences.getString("recently_accessed_courses", "")
            val type = object : TypeToken<MutableList<OfflineCourse>>() {}.type
            val savedList = gson.fromJson(json, type) as MutableList<OfflineCourse>

            if (savedList.contains(offlineCourse))
                savedList.remove(offlineCourse)


            savedList.add(0, offlineCourse)

            if (savedList.size > MAX_SIZE)
                savedList.removeAt(MAX_SIZE)

            val editor = sharedPreferences.edit()
            val json2 = gson.toJson(savedList)
            editor.putString("recently_accessed_courses", json2)
            editor.apply()
        } else {
            val savedList = mutableListOf<OfflineCourse>()
            savedList.add(offlineCourse)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(savedList)
            editor.putString("recently_accessed_courses", json)
            editor.apply()
        }


    }

    fun getAllRecentlyAccessedCourses(activity: FragmentActivity): List<OfflineCourse> {
        val sharedPreferences = activity.getPreferences(FragmentActivity.MODE_PRIVATE)
        var savedList = mutableListOf<OfflineCourse>()
        if (sharedPreferences.contains("recently_accessed_courses")) {
            val gson = Gson()
            val json = sharedPreferences.getString("recently_accessed_courses", "")
            val type = object : TypeToken<MutableList<OfflineCourse>>() {}.type
            savedList = gson.fromJson(json, type) as MutableList<OfflineCourse>
        }
        return savedList.toList()
    }

    fun deleteRecentlyAccessedCourse(offlineCourse: OfflineCourse, activity: FragmentActivity) {
        val sharedPreferences = activity.getPreferences(FragmentActivity.MODE_PRIVATE)
        if (sharedPreferences.contains("recently_accessed_courses")) {
            val savedList = getAllRecentlyAccessedCourses(activity).toMutableList()
            if (savedList.contains(offlineCourse)) {
                savedList.remove(offlineCourse)
                val editor = sharedPreferences.edit()
                val gson = Gson()
                val json = gson.toJson(savedList)
                editor.putString("recently_accessed_courses", json)
                editor.apply()
            } else
                Log.d(TAG, "deleteRecentlyAccessedCourse: No recently accessed courses found")
        }
    }

}