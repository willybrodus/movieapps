package com.company.myfavoritepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.ListMovieDto
import com.company.core.utility.rx.SchedulerProvider
import com.dicoding.whatsnewmoview.ui.base.BaseViewModel
import com.dicoding.whatsnewmoview.util.ext.addTo

class FavoriteViewModel @javax.inject.Inject constructor(
    private val movieUseCase: MovieUseCase, private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    private val _dataMovie = MutableLiveData<PagingData<ListMovieDto>>()
    val dataMovie: LiveData<PagingData<ListMovieDto>>
        get() = _dataMovie

    fun initGetListFavorite() {
        movieUseCase.getAllFilmFavorite()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ paging->
                _dataMovie.value = PagingData.from(paging)
            }, {
                onNetworkErrorHandling(it)
            }).addTo(compositeDisposable)
    }
}