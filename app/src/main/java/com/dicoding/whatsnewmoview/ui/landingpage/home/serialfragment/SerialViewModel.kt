package com.dicoding.whatsnewmoview.ui.landingpage.home.serialfragment

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

@HiltViewModel
class SerialViewModel @javax.inject.Inject constructor(
    private val movieUseCase: MovieUseCase, private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    private val _dataSerial = MutableLiveData<PagingData<ListMovieDto>>()
    val dataSerial: LiveData<PagingData<ListMovieDto>>
        get() = _dataSerial


    @ExperimentalCoroutinesApi
    fun getListSerial() {
        initGetListSerial()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ paging->
                _dataSerial.value = paging
            }, {
                onNetworkErrorHandling(it)
            }).addTo(compositeDisposable)
    }

    @ExperimentalCoroutinesApi
    fun initGetListSerial() : Flowable<PagingData<ListMovieDto>> {
        return movieUseCase.getSerialList().cachedIn(viewModelScope)
    }
}