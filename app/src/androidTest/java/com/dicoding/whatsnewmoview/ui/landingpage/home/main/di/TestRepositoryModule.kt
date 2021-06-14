package com.dicoding.whatsnewmoview.ui.landingpage.home.main.di

import com.company.core.data.repository.IMovieRepository
import com.company.core.data.repository.MovieRepository
import com.company.core.di.AppModule
import com.company.core.di.RepositoryModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
@Module
@TestInstallIn( components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule {
    @Binds
    abstract fun provideRepository(movieRepository: MovieRepository): IMovieRepository
}