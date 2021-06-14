package com.company.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.company.core.data.model.ListMovieDto
import com.company.core.data.source.db.DatabaseSource
import com.company.core.data.source.paging.FavoritePagingSource
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

    @ExperimentalCoroutinesApi
    override fun getAllFilmFavorite(): Flowable<PagingData<ListMovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { FavoritePagingSource(db) }
        ).flowable
    }

}