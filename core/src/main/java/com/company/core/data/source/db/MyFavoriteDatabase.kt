package com.company.core.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.company.core.data.model.ListMovieDto
import com.company.core.data.source.db.dao.FavoriteMovieDao
import com.company.core.utility.room.ConvertersArray

@Database(entities = [ListMovieDto::class], version = 1, exportSchema = false)
@TypeConverters(ConvertersArray::class)
abstract class MyFavoriteDatabase : RoomDatabase() {
    abstract fun favoriteFilm() : FavoriteMovieDao
}