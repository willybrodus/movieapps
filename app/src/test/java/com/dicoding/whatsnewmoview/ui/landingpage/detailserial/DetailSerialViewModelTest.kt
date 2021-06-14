package com.dicoding.whatsnewmoview.ui.landingpage.detailserial

import com.company.core.data.domain.MovieUseCase
import com.company.core.data.model.DetailSerialDto
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
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.net.SocketException

@ExperimentalCoroutinesApi
object DetailSerialViewModelTest : Spek({

    InstantTaskExecutorRule(this)
    val movieRepository = Mockito.mock(MovieUseCase::class.java)
    val viewModel = DetailSerialViewModel(movieRepository, TestSchedulerProvider())

    Feature("Detail Movie") { //ternyata 1 feature itu 1 lifecycle activity

        val data = ListMovieDto().apply {
            genreIds = listOf(1,2,3)
            id= 1
            originalTitle = "Falcon and Winter Solder"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Marvel : Falcon and Winter Solder"
        }

        val dataDetail = DetailSerialDto().apply {
            id = 1
            originalName = "Falcon and Winter Solder"
            overview = "Sample Testing"
            posterPath = null
            firstAirDate = "2021-02-14"
            name = "Marvel : Falcon and Winter Solder"
        }

        Scenario("get detail movie data properly ") {
            Given("Exception") {
                Mockito.`when`(movieRepository.getFilmFavorite(1)).thenReturn(
                    Maybe.just(data)
                )

                Mockito.`when`(movieRepository.getDetailSerial(1)).thenReturn(
                    Observable.just(dataDetail)
                )
            }

            When("request data") {
                viewModel.setSerial(data)
                viewModel.getDetailSerial()
            }

            Then("it should not empty data and title is true") {
                Assert.assertNotNull(viewModel.detailSerial.getOrAwaitValue())
                Assert.assertEquals(true ,viewModel.detailSerial.getOrAwaitValue() is RemoteState.RemoteData<DetailSerialDto>)
                val dataResult = viewModel.detailSerial.getOrAwaitValue() as RemoteState.RemoteData<DetailSerialDto>
                Assert.assertEquals("Falcon and Winter Solder" ,dataResult.result.originalName)
            }
        }

        Scenario("get detail movie data properly when network is troble") {
            Given("Exception") {
                Mockito.`when`(movieRepository.getDetailSerial(1)).thenReturn(
                    Observable.error(SocketException("Time Out Exception"))
                )
            }

            When("request data") {
                viewModel.setSerial(data)
                viewModel.getDetailSerial()
            }

            Then("Should be _throwable not null and is StatusConnectException") {
                Assert.assertNotNull(viewModel.throwable.getOrAwaitValue())
                Assert.assertEquals(true, viewModel.throwable.getOrAwaitValue() is StatusConnection.StatusConnectException)
            }
        }
    }
})