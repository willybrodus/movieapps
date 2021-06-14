package com.dicoding.whatsnewmoview.ui.landingpage.home.serialfragment

import androidx.paging.PagingData
import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.ListMovieDto
import com.dicoding.whatsnewmoview.InstantTaskExecutorRule
import com.dicoding.whatsnewmoview.TestSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SerialViewModelTest : Spek({

    InstantTaskExecutorRule(this)
    val movieRepository = mock(MovieUseCase::class.java)
    val viewModel = SerialViewModel(movieRepository, TestSchedulerProvider())
    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    beforeEachTest {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    afterEachTest {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    Feature("Serial") { //ternyata 1 feature itu 1 lifecycle activity
        Scenario("get Serial data properly when data is empty") {
            var resultData : Flowable<PagingData<ListMovieDto>>? = null
            val dataTarget = PagingData.empty<ListMovieDto>()
            Given("overview data") {
                doReturn(Flowable.just(dataTarget)).`when`(movieRepository).getSerialList()
                `when`(movieRepository.getSerialList()).thenReturn(Flowable.just(dataTarget))
            }

            When("request data") {
                resultData = viewModel.initGetListSerial()
            }

            Then("it should not null") {
                val result = resultData?.take(1)?.toList()
                Assert.assertNotNull(result)
            }

        }

        Scenario("get Serial data properly") {
            var resultData : Flowable<PagingData<ListMovieDto>>? = null
            val dataTarget = PagingData.from(listOf(ListMovieDto().apply {
                genreIds = listOf(1,2,3)
                id= 1
                originalTitle = "Falcon and Winter Solder"
                overview = "Sample Testing"
                posterPath = null
                releaseDate = "2021-02-14"
                title = "Marvel : Falcon and Winter Solder"
            }))

            Given("List Serial") {
                doReturn(Single.just(dataTarget).toFlowable()).whenever(movieRepository)
                    .getSerialList()
                whenever(movieRepository.getSerialList())
                    .thenReturn(Single.just(dataTarget).toFlowable())
            }

            When("request data") {
                resultData =  viewModel.initGetListSerial()
            }

            Then("it should not empty data") {
                val result = resultData?.take(1)?.toList()
                Assert.assertNotNull(result)
            }
        }
    }
})