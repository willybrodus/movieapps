package com.dicoding.whatsnewmoview.ui.landingpage.home.main

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.company.core.data.model.ListMovieDto
import com.company.core.di.AppModule
import com.company.core.di.NetworkModule
import com.dicoding.whatsnewmoview.R
import com.dicoding.whatsnewmoview.ui.landingpage.detailmovie.DetailMovieActivity
import com.dicoding.whatsnewmoview.ui.landingpage.detailserial.DetailSerialActivity
import com.dicoding.whatsnewmoview.util.RxIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@UninstallModules(AppModule::class, NetworkModule::class)
@HiltAndroidTest
class LandingActivityTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(LandingActivity::class.java, true, false)

    @Rule
    @JvmField
    val detailActivityRule = ActivityTestRule(DetailMovieActivity::class.java, true, false)

    @Rule
    @JvmField
    val detailSerialActivityRule = ActivityTestRule(DetailSerialActivity::class.java, true, false)

    private lateinit var rxIdlingResource : RxIdlingResource

    val data = ListMovieDto().apply {
        genreIds = listOf(1, 2, 3)
        id = 399566
        originalTitle = "Godzilla vs Kong"
        overview = "Sample Testing"
        posterPath = null
        releaseDate = "2021-02-14"
        title = "Godzilla vs Kong"
    }

    val dataSerial = ListMovieDto().apply {
        genreIds = listOf(1, 2, 3)
        id= 1416
        originalTitle = "Grey's Anatomy"
        overview = "Sample Testing"
        posterPath = null
        releaseDate = "2017-09-25"
        title = "Grey's Anatomy"
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        rxIdlingResource = RxIdlingResource()
        rxIdlingResource.register()
    }



    @Test
    fun loadCinema() {
        activityRule.launchActivity(null)
        onView(allOf(withId(R.id.pb_loading), isDisplayed()))
        onView(allOf(withId(R.id.rv_list_cinema), isDisplayed()))
    }

    @Test
    fun loadDetailCinema() {
        activityRule.launchActivity(null)
        onView(allOf(withId(R.id.pb_loading), isDisplayed()))
        rxIdlingResource.waitForIdle()
        onView(allOf(withId(R.id.rv_list_cinema), isDisplayed())).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )

        val intent = Intent()
        intent.putExtra(ListMovieDto::class.java.simpleName, data)
        detailActivityRule.launchActivity(intent)
        onView(
            allOf(
                withId(R.id.txt_title),
                isDisplayed()
            )
        ).check(matches(withText("Godzilla vs. Kong")))
    }


    @Test
    fun loadListSerial() {
        activityRule.launchActivity(null)
        rxIdlingResource.waitForIdle()
        onView(allOf(withId(R.id.rv_list_cinema), isDisplayed()))
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_serial), withContentDescription("Tv Show"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(ViewActions.click())
        rxIdlingResource.waitForIdle()
        onView(allOf(withId(R.id.rv_list), isDisplayed()))
    }

    @Test
    fun loadDetailSerial() {
        activityRule.launchActivity(null)
        rxIdlingResource.waitForIdle()
        onView(allOf(withId(R.id.rv_list_cinema), isDisplayed()))
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_serial), withContentDescription("Tv Show"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(ViewActions.click())
        onView(allOf(withId(R.id.rv_list), isDisplayed())).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )

        val intent = Intent()
        intent.putExtra(ListMovieDto::class.java.simpleName, dataSerial)
        detailSerialActivityRule.launchActivity(intent)
        rxIdlingResource.waitForIdle()
        onView(
            allOf(
                withId(R.id.txt_title),
                isDisplayed()
            )
        ).check(matches(withText("Grey's Anatomy")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}