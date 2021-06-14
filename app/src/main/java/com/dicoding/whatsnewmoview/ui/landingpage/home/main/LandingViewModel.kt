package com.dicoding.whatsnewmoview.ui.landingpage.home.main

import com.company.core.data.domain.MovieUseCase
import com.dicoding.whatsnewmoview.ui.base.BaseViewModel
import com.company.core.utility.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    val movieUseCase: MovieUseCase, val schedulerProvider: SchedulerProvider
) : BaseViewModel()