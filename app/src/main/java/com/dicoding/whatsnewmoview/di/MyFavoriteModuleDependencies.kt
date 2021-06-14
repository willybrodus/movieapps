package com.dicoding.whatsnewmoview.di

import com.company.core.data.domain.MovieUseCase
import com.company.core.utility.rx.SchedulerProvider
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MyFavoriteModuleDependencies {

    fun movieUseCase(): MovieUseCase

    fun provideSchedulerProvider(): SchedulerProvider
}