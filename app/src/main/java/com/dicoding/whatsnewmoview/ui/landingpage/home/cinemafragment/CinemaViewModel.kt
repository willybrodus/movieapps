package com.dicoding.whatsnewmoview.ui.landingpage.home.cinemafragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.ListMovieDto
import com.dicoding.whatsnewmoview.ui.base.BaseViewModel
import com.dicoding.whatsnewmoview.util.ext.addTo
import com.company.core.utility.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class CinemaViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase, private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _dataMovie = MutableLiveData<PagingData<ListMovieDto>>()
    val dataMovie: LiveData<PagingData<ListMovieDto>>
        get() = _dataMovie

    @ExperimentalCoroutinesApi
    fun initGetListMovie(): Flowable<PagingData<ListMovieDto>> {
        return movieUseCase.getMovieList().cachedIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    fun getListMovie() {
        initGetListMovie()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ paging->
                _dataMovie.value = paging
            }, {
                onNetworkErrorHandling(it)
            }).addTo(compositeDisposable)
    }

}