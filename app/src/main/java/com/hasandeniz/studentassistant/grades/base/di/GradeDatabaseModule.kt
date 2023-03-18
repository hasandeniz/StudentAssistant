package com.hasandeniz.studentassistant.grades.base.di

import android.content.Context
import androidx.room.Room
import com.hasandeniz.studentassistant.grades.base.data.room.GradeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GradeDatabaseModule {

    @Provides
    @Singleton
    fun provideGradeDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GradeDatabase::class.java, "grade_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideGradeDao(database: GradeDatabase) = database.gradeDao()
}