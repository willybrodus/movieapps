package com.dicoding.whatsnewmoview.ui.landingpage.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.model.RemoteState
import com.company.core.data.model.RemoteState.*
import com.dicoding.whatsnewmoview.ui.base.BaseViewModel
import com.dicoding.whatsnewmoview.util.ext.addTo
import com.company.core.utility.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase, private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private lateinit var movieDto: ListMovieDto

    private val _detailMovie = MutableLiveData<RemoteState<DetailMovieDto>>()
    val detailMovie: LiveData<RemoteState<DetailMovieDto>>
        get() = _detailMovie

    fun setMovie(movieDto: ListMovieDto) {
        this.movieDto = movieDto
        this.movieDto.apply {
            isSerial = false
        }
    }

    fun getDetailMoview() {
        _detailMovie.value = InProgress(true)
        movieUseCase.getDetailMovie(movieDto.id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .flatMap {
                _detailMovie.value = InProgress(false)
                _detailMovie.value = RemoteData(it)
                return@flatMap getDetailFilmInFavorite(it.id ?: 0)
            }
            .subscribe({
                val data = _detailMovie.value as RemoteData
                data.result.isFavorite = true
                _detailMovie.value = data
            }, {
                _detailMovie.value = InProgress(false)
                onNetworkErrorHandling(it)
            }).addTo(compositeDisposable)
    }

    private fun getDetailFilmInFavorite(id: Int): Observable<ListMovieDto?> {
        return movieUseCase.getFilmFavorite(id)
            .toObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun saveToFavorite() {
        viewModelScope.launch {
            movieUseCase.addFilmToFavorite(movieDto)
        }
    }

    fun deleteFromFavorite() {
        viewModelScope.launch {
            movieUseCase.deleteFilmFromFavorite(movieDto)
        }
    }
}