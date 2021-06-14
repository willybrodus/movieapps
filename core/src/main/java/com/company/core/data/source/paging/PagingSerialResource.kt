package com.company.core.data.source.paging

import androidx.paging.PagingState
import com.company.core.data.model.ListMovieDto
import com.company.core.data.source.remote.Api
import com.company.core.utility.paging.BaseRxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SerialPagingSource(private val service: Api) :
    BaseRxPagingSource<ListMovieDto>(  1) {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ListMovieDto>> {
        val position = params.key ?: 1
        return service.serialNowPlaying(page = position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, params) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, ListMovieDto>): Int? {
        return null
    }
}