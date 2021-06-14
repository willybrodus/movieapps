package com.company.core.data.source.db.dao

import androidx.room.*
import com.company.core.data.model.ListMovieDto
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM FAVORITEMOVIE")
    fun getAllFavorite(): Single<List<ListMovieDto>>

    @Query("SELECT * FROM FAVORITEMOVIE WHERE ID = :idData")
    fun getFavoriteById(idData: String): Maybe<ListMovieDto>

    @Query("DELETE FROM FAVORITEMOVIE")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(data: ListMovieDto) : Long

    @Delete
    suspend fun delete(dataFavorite: ListMovieDto)

    @Query("DELETE FROM FAVORITEMOVIE WHERE ID = :idData")
    suspend fun deleteById(idData: String) : Int
}