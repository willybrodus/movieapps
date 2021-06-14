package com.company.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.company.core.data.model.ListMovieDto
import com.company.core.data.source.paging.MoviePagingSource
import com.company.core.data.source.paging.SerialPagingSource
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class AppRemoteSource @Inject constructor(private val api: Api) {

    @ExperimentalCoroutinesApi
    fun getMovieList() : Flowable<PagingData<ListMovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { MoviePagingSource(api) }
        ).flowable
    }

    @ExperimentalCoroutinesApi
    fun getSerialList() : Flowable<PagingData<ListMovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { SerialPagingSource(api) }
        ).flowable
    }

    fun getDetailMovie(id: Int) = api.detailMovie(id)

    fun getDetailSerial(id: Int) = api.detailSerial(id)

}