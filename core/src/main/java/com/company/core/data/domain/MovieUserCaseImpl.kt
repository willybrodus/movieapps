package com.company.core.data.domain

import androidx.paging.PagingData
import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.DetailSerialDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.repository.MovieRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class MovieUserCaseImpl@Inject constructor(private val movieRepository: MovieRepository) : MovieUseCase {
    @ExperimentalCoroutinesApi
    override fun getMovieList(): Flowable<PagingData<ListMovieDto>>  = movieRepository.getMovieList()

    @ExperimentalCoroutinesApi
    override fun getSerialList(): Flowable<PagingData<ListMovieDto>> = movieRepository.getSerialList()

    override fun getDetailMovie(id: Int): Observable<DetailMovieDto> = movieRepository.getDetailMovie(id)

    override fun getDetailSerial(id: Int): Observable<DetailSerialDto> = movieRepository.getDetailSerial(id)

    override suspend fun addFilmToFavorite(listMovie: ListMovieDto): Long = movieRepository.addFilmToFavorite(listMovie)

    override suspend fun deleteFilmFromFavorite(listMovie: ListMovieDto) = movieRepository.deleteFilmFromFavorite(listMovie)

    override fun getFilmFavorite(id: Int): Maybe<ListMovieDto> = movieRepository.getFilmFavorite(id)

    @ExperimentalCoroutinesApi
    override fun getAllFilmFavorite(): Flowable<PagingData<ListMovieDto>> = movieRepository.getAllFilmFavorite()
}