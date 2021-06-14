package com.dicoding.whatsnewmoview.ui.landingpage.home.main.di

import android.content.Context
import androidx.room.Room
import com.company.core.di.DbModule
import com.company.core.utility.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@TestInstallIn( components = [SingletonComponent::class],
    replaces = [DbModule::class]
)
class TestDbModule {

    val passphrase: ByteArray = SQLiteDatabase.getBytes("movielist".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        com.company.core.data.source.db.MyFavoriteDatabase::class.java,
        Constant.TABLE_NAME
    ).fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()
}