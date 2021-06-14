package com.dicoding.whatsnewmoview.data.repository

import androidx.paging.PagingData
import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.DetailSerialDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.repository.MovieRepository
import com.company.core.data.source.db.DatabaseSource
import com.company.core.data.source.remote.AppRemoteSource
import com.dicoding.whatsnewmoview.InstantTaskExecutorRule
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.mockito.Mockito
import org.mockito.kotlin.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
object MovieRepositoryTest : Spek({
    InstantTaskExecutorRule(this)
    val appRemoteSource = Mockito.mock(AppRemoteSource::class.java)
    val dbSource = Mockito.mock(DatabaseSource::class.java)
    val repositoryClass = MovieRepository(appRemoteSource, dbSource)
    val mainThreadSurrogate = newSingleThreadContext("UI thread")


    beforeEachTest {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    afterEachTest {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    Feature("Testing Repository") { //ternyata 1 feature itu 1 lifecycle activity
        val emptyDataMovie = PagingData.empty<ListMovieDto>()
        val listDataMovie = PagingData.from(listOf(ListMovieDto().apply {
            genreIds = listOf(1, 2, 3)
            id = 1
            originalTitle = "Goodzila vs Kong"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Goodzila vs Kong"
        }))
        val dataDetailMovie = DetailMovieDto().apply {
            id = 1
            originalTitle = "Goodzila vs Kong"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Goodzila vs Kong"
        }

        Scenario("get List Movie data properly when data is empty") {
            var result: Flowable<PagingData<ListMovieDto>>? = null

            Given("List Movie") {
                Mockito.doReturn(Flowable.just(emptyDataMovie)).`when`(appRemoteSource)
                    .getMovieList()
                Mockito.`when`(appRemoteSource.getMovieList())
                    .thenReturn(Flowable.just(emptyDataMovie))
            }

            When("request data") {
                result = repositoryClass.getMovieList()
            }

            Then("it should be empty data") {
                Mockito.verify(appRemoteSource).getMovieList()
                result?.test()?.assertValue(PagingData.empty())
            }
        }

        Scenario("get List Movie data properly when data is NOT empty") {

            var result: Flowable<PagingData<ListMovieDto>>? = null

            Given("List Movie") {
                Mockito.doReturn(Flowable.just(listDataMovie)).`when`(appRemoteSource)
                    .getMovieList()
                Mockito.`when`(appRemoteSource.getMovieList())
                    .thenReturn(Flowable.just(listDataMovie))
            }

            When("request data") {
                result = repositoryClass.getMovieList()
            }

            Then("it should not empty data") {
                result?.test()?.assertValue(listDataMovie)
            }
        }

        //Detail Movie
        var detailMovie: Observable<DetailMovieDto>? = null
        var assertionDetailMovie: DetailMovieDto? = null

        Scenario("get Detail Movie data properly") {
            Given("Detail Movie of Goodzila vs Kong") {
                Mockito.doReturn(Observable.just(dataDetailMovie)).`when`(appRemoteSource)
                    .getDetailMovie(1)
                Mockito.`when`(appRemoteSource.getDetailMovie(1))
                    .thenReturn(Observable.just(dataDetailMovie))
            }

            When("request data") {
                detailMovie = repositoryClass.getDetailMovie(1)
            }

            Then("it should be Detail Movie of Goodzila vs Kong") {
                Mockito.verify(appRemoteSource).getDetailMovie(1)
                detailMovie?.subscribe({
                    assertionDetailMovie = it
                }, { error ->
                    error.printStackTrace()
                })

                Assert.assertEquals(true, assertionDetailMovie != null)
                Assert.assertEquals(true, assertionDetailMovie is DetailMovieDto)
                Assert.assertEquals("Goodzila vs Kong", assertionDetailMovie?.originalTitle)
                Assert.assertEquals("Goodzila vs Kong", assertionDetailMovie?.title)
                Assert.assertEquals(1, assertionDetailMovie?.id)
                Assert.assertEquals("2021-02-14", assertionDetailMovie?.releaseDate)
            }
        }

        val emptyDataSerial = PagingData.empty<ListMovieDto>()
        val listDataSerial = PagingData.from(arrayListOf(ListMovieDto().apply {
            genreIds = listOf(1, 2, 3)
            id = 1
            originalTitle = "Falcon and Winter Solder"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Marvel : Falcon and Winter Solder"
        }))
        val dataDetailSerial = DetailSerialDto().apply {
            id = 1
            originalName = "Falcon and Winter Solder"
            overview = "Sample Testing"
            posterPath = null
            firstAirDate = "2021-02-14"
            name = "Marvel : Falcon and Winter Solder"
        }

        Scenario("get List Serial data properly when data is empty") {
            var result: Flowable<PagingData<ListMovieDto>>? = null
            Given("List Movie") {
                Mockito.doReturn(Flowable.just(emptyDataSerial)).`when`(appRemoteSource)
                    .getSerialList()
                Mockito.`when`(appRemoteSource.getSerialList())
                    .thenReturn(Flowable.just(emptyDataSerial))
            }

            When("request data") {
                result = repositoryClass.getSerialList()
            }

            Then("it should be empty data") {
                Mockito.verify(appRemoteSource).getSerialList()
                result?.test()?.assertValue(PagingData.empty())
            }
        }

        Scenario("get List Serial data properly when data is NOT empty") {

            var result: Flowable<PagingData<ListMovieDto>>? = null

            Given("List Movie") {
                Mockito.doReturn(Flowable.just(listDataSerial)).`when`(appRemoteSource)
                    .getSerialList()
                Mockito.`when`(appRemoteSource.getSerialList())
                    .thenReturn(Flowable.just(listDataSerial))
            }

            When("request data") {
                result = repositoryClass.getSerialList()
            }

            Then("it should not empty data") {
                result?.test()?.assertValue(listDataSerial)
            }
        }

        var detailSerial: Observable<DetailSerialDto>? = null
        var assertionDetailSerial: DetailSerialDto? = null
        Scenario("get Detail Serial data properly") {
            Given("Detail Serial of Falcon and Winter Solder") {
                Mockito.doReturn(Observable.just(dataDetailSerial)).`when`(appRemoteSource)
                    .getDetailSerial(1)
                Mockito.`when`(appRemoteSource.getDetailSerial(1))
                    .thenReturn(Observable.just(dataDetailSerial))
            }

            When("request data") {
                detailSerial = repositoryClass.getDetailSerial(1)
            }

            Then("it should be Detail Serial of Falcon and Winter Solder") {
                Mockito.verify(appRemoteSource).getDetailSerial(1)
                detailSerial?.subscribe({
                    assertionDetailSerial = it
                }, { error ->
                    error.printStackTrace()
                })

                Assert.assertEquals(true, assertionDetailSerial != null)
                Assert.assertEquals(true, assertionDetailSerial is DetailSerialDto)
                Assert.assertEquals("Falcon and Winter Solder", assertionDetailSerial?.originalName)
                Assert.assertEquals(
                    "Marvel : Falcon and Winter Solder",
                    assertionDetailSerial?.name
                )
                Assert.assertEquals(1, assertionDetailSerial?.id)
                Assert.assertEquals("2021-02-14", assertionDetailSerial?.firstAirDate)
            }
        }

        //TODO LIST FROM DB
        val data = ListMovieDto().apply {
            genreIds = listOf(1, 2, 3)
            id = 1
            originalTitle = "Falcon and Winter Solder"
            overview = "Sample Testing"
            posterPath = null
            releaseDate = "2021-02-14"
            title = "Marvel : Falcon and Winter Solder"
            isSerial = true
        }
        val listDataDB = arrayListOf(data)

        Scenario("get List of Favorite data properly") {
            var result: Flowable<PagingData<ListMovieDto>>? = null
            Given("Detail Serial of Falcon and Winter Solder") {
                Mockito.doReturn(Single.just(listDataDB)).`when`(dbSource).getAllFilmFavorite()
                Mockito.`when`(dbSource.getAllFilmFavorite())
                    .thenReturn(Single.just(listDataDB))
            }

            When("request data") {
                result = repositoryClass.getAllFilmFavorite()
            }

            Then("It should be not null") {
                var resultPage: PagingData<ListMovieDto>? = null
                result?.subscribe ({
                    resultPage = it
                }, { error ->
                    error.printStackTrace()
                })

                Assert.assertNotNull(resultPage)
            }
        }

        Scenario("get data of Favorite data properly") {
            var result: Maybe<ListMovieDto>? = null
            Given("Detail Serial of Falcon and Winter Solder") {
                Mockito.doReturn(Maybe.just(data)).`when`(dbSource).getFilmFavorite("1")
                Mockito.`when`(dbSource.getFilmFavorite("1"))
                    .thenReturn(Maybe.just(data))
            }

            When("request data") {
                result = repositoryClass.getFilmFavorite(1)
            }

            Then("It should be not null") {
                var resultPage: ListMovieDto? = null
                result?.subscribe ({
                    resultPage = it
                }, { error ->
                    error.printStackTrace()
                })

                Assert.assertNotNull(resultPage)
                Assert.assertEquals("Falcon and Winter Solder",resultPage?.originalTitle)
            }
        }

        Scenario("delete List of Favorite data properly") {
            When("request data") {
                runBlocking {
                    repositoryClass.deleteFilmFromFavorite(data)
                }
            }

            Then("It should be true") {
                runBlocking {
                    verify(dbSource).deleteFilmFromFavorite(data)
                }
            }
        }

        Scenario("add List of Favorite data properly") {
            var result: Long? = null
            Given("Detail Serial of Falcon and Winter Solder") {
                runBlocking {
                    Mockito.doReturn(1L).`when`(dbSource).addFilmToFavorite(data)
                    Mockito.`when`(dbSource.addFilmToFavorite(data))
                        .thenReturn(1L)
                }
            }

            When("request data") {
                runBlocking {
                    result = repositoryClass.addFilmToFavorite(data)
                }
            }

            Then("It should be not null") {
                Assert.assertNotNull(result)
                Assert.assertEquals(1L, result)
            }
        }

    }

})