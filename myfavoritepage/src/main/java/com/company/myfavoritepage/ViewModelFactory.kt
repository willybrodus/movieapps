package com.company.myfavoritepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.core.data.domain.MovieUseCase
import com.company.core.utility.rx.SchedulerProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase, private val schedulerProvider: SchedulerProvider) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieUseCase, schedulerProvider) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}