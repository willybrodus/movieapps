package com.company.core.data.source.paging

import androidx.paging.PagingState
import com.company.core.data.model.ListMovieDto
import com.company.core.data.source.db.DatabaseSource
import com.company.core.utility.paging.BaseRxPagingSourceRoom
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FavoritePagingSource (private val db: DatabaseSource) : BaseRxPagingSourceRoom<ListMovieDto>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ListMovieDto>> {
        return db.getAllFilmFavorite()
            .subscribeOn(Schedulers.io())
            .map {
                toLoadResult(it, params)
            }
            .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, ListMovieDto>): Int? {
        return null
    }
}