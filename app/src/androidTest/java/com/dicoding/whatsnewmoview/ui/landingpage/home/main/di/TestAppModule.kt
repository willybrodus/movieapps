package com.dicoding.whatsnewmoview.ui.landingpage.home.main.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.company.core.data.source.db.MyFavoriteDatabase
import com.company.core.di.AppModule
import com.company.core.utility.Constant
import com.company.core.utility.rx.AppSchedulerProvider
import com.company.core.utility.rx.SchedulerProvider
import com.dicoding.whatsnewmoview.R
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.inflationx.calligraphy3.CalligraphyConfig
import javax.inject.Singleton

@Module
@TestInstallIn( components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {
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
            .setFontAttrId(com.company.core.R.attr.fontPath)
            .build()
    }
}