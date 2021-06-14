package com.company.core.di

import android.content.Context
import androidx.room.Room
import com.company.core.utility.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

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