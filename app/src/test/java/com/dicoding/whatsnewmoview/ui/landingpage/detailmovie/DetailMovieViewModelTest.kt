package com.dicoding.whatsnewmoview.ui.landingpage.detailmovie

import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.model.RemoteState
import com.dicoding.whatsnewmoview.InstantTaskExecutorRule
import com.dicoding.whatsnewmoview.TestSchedulerProvider
import com.dicoding.whatsnewmoview.util.StatusConnection
import com.dicoding.whatsnewmoview.util.ext.getOrAwaitValue
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.mockito.Mockito.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.net.SocketException

@ExperimentalCoroutinesApi
object DetailMovieViewModelTest : Spek({

    InstantTaskExecutorRule(this)
    val movieRepository = mock(MovieUseCase::class.java)
    val viewModel = DetailMovieViewModel(movieRepository, TestSchedulerProvider())

    Feature("Detail Movie") { //ternyata 1 feature itu 1 lifecycle activity

        val data = ListMovieDto().apply {
            genreIds = listOf(1, 2, 3)
            id= 1
            originalTitle = "Goodzila vs Kong"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Goodzila vs Kong"
        }

        val dataDetail = DetailMovieDto().apply {
            id = 1
            originalTitle = "Goodzila vs Kong"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Goodzila vs Kong"
        }

        Scenario("get detail movie data properly ") {
            Given("Data detail") {
                `when`(movieRepository.getFilmFavorite(1)).thenReturn(
                    Maybe.just(data)
                )

                `when`(movieRepository.getDetailMovie(1)).thenReturn(
                    Observable.just(dataDetail)
                )
            }

            When("request data") {
                viewModel.setMovie(data)
                viewModel.getDetailMoview()
            }

            Then("it should not empty data and title is true") {
                val dataResult = viewModel.detailMovie.getOrAwaitValue() as RemoteState.RemoteData<DetailMovieDto>
                Assert.assertNotNull(viewModel.detailMovie.getOrAwaitValue())
                Assert.assertEquals(true ,viewModel.detailMovie.getOrAwaitValue() is RemoteState.RemoteData<DetailMovieDto>)
                Assert.assertEquals("Goodzila vs Kong" ,dataResult.result.originalTitle)
            }
        }

        Scenario("get detail movie data properly when network is troble") {
            Given("Exception") {
                `when`(movieRepository.getDetailMovie(1)).thenReturn(
                    Observable.error(SocketException("Time Out Exception"))
                )
            }

            When("request data") {
                viewModel.setMovie(data)
                viewModel.getDetailMoview()
            }

            Then("Should be _throwable not null and is StatusConnectException") {
                Assert.assertNotNull(viewModel.throwable.getOrAwaitValue())
                Assert.assertTrue(viewModel.throwable.getOrAwaitValue() is StatusConnection.StatusConnectException)
            }
        }
    }
})