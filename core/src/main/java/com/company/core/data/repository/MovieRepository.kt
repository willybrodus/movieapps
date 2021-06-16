package com.company.core.data.repository

import androidx.paging.PagingData
import com.company.core.data.model.ListMovieDto
import com.company.core.data.source.db.DatabaseSource
import com.company.core.data.source.remote.AppRemoteSource
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val api: AppRemoteSource, private val db : DatabaseSource) : IMovieRepository {

    @ExperimentalCoroutinesApi
    override fun getMovieList(): Flowable<PagingData<ListMovieDto>> {
        return api.getMovieList()
    }

    @ExperimentalCoroutinesApi
    override fun getSerialList() = api.getSerialList()

    override fun getDetailMovie(id : Int) = api.getDetailMovie(id)

    override fun getDetailSerial(id : Int) = api.getDetailSerial(id)

    override suspend fun addFilmToFavorite(listMovie : ListMovieDto) = db.addFilmToFavorite(listMovie)

    override suspend fun deleteFilmFromFavorite(listMovie : ListMovieDto) = db.deleteFilmFromFavorite(listMovie)

    override fun getFilmFavorite(id : Int) = db.getFilmFavorite(id.toString())

    override fun getAllFilmFavorite(): Flowable<List<ListMovieDto>> = db.getAllFilmFavorite()

}