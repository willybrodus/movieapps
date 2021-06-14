package com.dicoding.whatsnewmoview.ui.landingpage.home.cinemafragment

import androidx.paging.PagingData
import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.ListMovieDto
import com.dicoding.whatsnewmoview.InstantTaskExecutorRule
import com.dicoding.whatsnewmoview.TestSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CinemaViewModelTest : Spek({

    InstantTaskExecutorRule(this)

    val movieRepository = mock(MovieUseCase::class.java)
    val viewModel = CinemaViewModel(movieRepository, TestSchedulerProvider())
    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    beforeEachTest {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    afterEachTest {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    Feature("Cinema") { //ternyata 1 feature itu 1 lifecycle activity
        Scenario("get cinema data properly when data is empty") {
            var resultData : Flowable<PagingData<ListMovieDto>>? = null
            val dataTarget = PagingData.empty<ListMovieDto>()
            Given("overview data") {
                doReturn(Flowable.just(dataTarget)).`when`(movieRepository).getMovieList()
                `when`(movieRepository.getMovieList()).thenReturn(Flowable.just(dataTarget))
            }

            When("request data") {
                resultData = viewModel.initGetListMovie()
            }

            Then("it should not null") {
                val result = resultData?.take(1)?.toList()
                Assert.assertNotNull(result)
            }

        }

        Scenario("get cinema data properly") {
            var resultData : Flowable<PagingData<ListMovieDto>>? = null
            val dataTarget = PagingData.from(listOf(ListMovieDto().apply {
                genreIds = listOf(1, 2, 3)
                id = 1
                originalTitle = "Goodzila vs Kong"
                overview = "Sample Testing"
                posterPath = null
                releaseDate = "2021-02-14"
                title = "Goodzila vs Kong"
            }))

            Given("List Movie") {
                doReturn(Single.just(dataTarget).toFlowable()).whenever(movieRepository)
                    .getMovieList()
                whenever(movieRepository.getMovieList())
                    .thenReturn(Single.just(dataTarget).toFlowable())
            }

            When("request data") {
                resultData =  viewModel.initGetListMovie()
            }

            Then("it should not empty data") {
                val result = resultData?.take(1)?.toList()
                Assert.assertNotNull(result)
            }
        }
    }
})