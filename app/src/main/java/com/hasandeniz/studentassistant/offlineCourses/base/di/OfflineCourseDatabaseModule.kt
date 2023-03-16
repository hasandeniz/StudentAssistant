package com.hasandeniz.studentassistant.offlineCourses.base.di

import android.content.Context
import androidx.room.Room
import com.hasandeniz.studentassistant.offlineCourses.base.data.room.OfflineCoursesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OfflineCourseDatabaseModule {

    @Provides
    @Singleton
    fun provideOfflineCoursesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, OfflineCoursesDatabase::class.java, "offline_course_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOfflineCourseDao(database: OfflineCoursesDatabase) = database.offlineCourseDao()
}