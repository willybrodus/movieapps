package com.company.core.data.source.db

import com.company.core.data.model.ListMovieDto
import javax.inject.Inject

class DatabaseSource @Inject constructor(private val db : MyFavoriteDatabase) {

    suspend fun addFilmToFavorite(listMovie : ListMovieDto) = db.favoriteFilm().add(listMovie)

    suspend fun deleteFilmFromFavorite(listMovie : ListMovieDto) = db.favoriteFilm().delete(listMovie)

    fun getAllFilmFavorite() = db.favoriteFilm().getAllFavorite()

    fun getFilmFavorite(id : String) = db.favoriteFilm().getFavoriteById(id)

}