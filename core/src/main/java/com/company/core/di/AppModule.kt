package com.company.core.di

import android.app.Application
import android.content.Context
import com.company.core.R
import com.company.core.utility.rx.AppSchedulerProvider
import com.company.core.utility.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application) : Context = application

    @Singleton
    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    internal fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/GoogleSans-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

}