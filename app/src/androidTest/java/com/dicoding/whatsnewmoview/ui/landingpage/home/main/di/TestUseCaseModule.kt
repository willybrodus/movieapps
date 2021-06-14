package com.dicoding.whatsnewmoview.ui.landingpage.home.main.di

import com.company.core.data.domain.MovieUseCase
import com.company.core.data.domain.MovieUserCaseImpl
import com.company.core.di.RepositoryModule
import com.company.core.di.UseCaseModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn( components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
abstract class TestUseCaseModule {

    @Binds
    abstract fun provideMovieUseCase(moveUseCase: MovieUserCaseImpl): MovieUseCase
}