package com.company.core.di

import com.company.core.data.domain.MovieUseCase
import com.company.core.data.domain.MovieUserCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun provideMovieUseCase(moveUseCase: MovieUserCaseImpl): MovieUseCase
}