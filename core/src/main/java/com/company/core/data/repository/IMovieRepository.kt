package com.company.core.data.repository

import androidx.paging.PagingData
import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.DetailSerialDto
import com.company.core.data.model.ListMovieDto
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

interface IMovieRepository {
    fun getMovieList(): Flowable<PagingData<ListMovieDto>>

    fun getSerialList(): Flowable<PagingData<ListMovieDto>>

    fun getDetailMovie(id: Int): Observable<DetailMovieDto>

    fun getDetailSerial(id: Int): Observable<DetailSerialDto>

    suspend fun addFilmToFavorite(listMovie: ListMovieDto): Long

    suspend fun deleteFilmFromFavorite(listMovie: ListMovieDto)

    fun getFilmFavorite(id: Int): Maybe<ListMovieDto>

    fun getAllFilmFavorite(): Flowable<List<ListMovieDto>>
}