package com.dicoding.whatsnewmoview.ui.landingpage.detailserial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.DetailSerialDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.model.RemoteState
import com.dicoding.whatsnewmoview.ui.base.BaseViewModel
import com.dicoding.whatsnewmoview.util.ext.addTo
import com.company.core.utility.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSerialViewModel @Inject constructor(
    private val moveUseCase: MovieUseCase, private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private lateinit var serialDto: ListMovieDto

    private val _detailSerial = MutableLiveData<RemoteState<DetailSerialDto>>()
    val detailSerial: LiveData<RemoteState<DetailSerialDto>>
    get() = _detailSerial

    fun setSerial(serialDto: ListMovieDto) {
        this.serialDto = serialDto
        this.serialDto.apply {
            isSerial = true
        }
    }

    fun getDetailSerial() {
        _detailSerial.value = RemoteState.InProgress(true)
        moveUseCase.getDetailSerial(serialDto.id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .flatMap {
                _detailSerial.value = RemoteState.InProgress(false)
                _detailSerial.value = RemoteState.RemoteData(it)
                return@flatMap getDetailSerialInFavorite(it.id ?: 0)
            }
            .subscribe({
                val data = _detailSerial.value as RemoteState.RemoteData
                data.result.isFavorite = true
                _detailSerial.value = data
            }, {
                _detailSerial.value = RemoteState.InProgress(false)
                onNetworkErrorHandling(it)
            }).addTo(compositeDisposable)
    }


    private fun getDetailSerialInFavorite(id: Int): Observable<ListMovieDto?> {
        return moveUseCase.getFilmFavorite(id)
            .toObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun saveToFavorite(){
        viewModelScope.launch {
            moveUseCase.addFilmToFavorite(serialDto)
        }
    }

    fun deleteFromFavorite(){
        viewModelScope.launch {
            moveUseCase.deleteFilmFromFavorite(serialDto)
        }
    }
}