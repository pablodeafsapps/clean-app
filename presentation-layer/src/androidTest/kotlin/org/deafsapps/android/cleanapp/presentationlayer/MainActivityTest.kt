package org.deafsapps.android.cleanapp.presentationlayer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.deafsapps.android.cleanapp.presentationlayer.di.presentationLayerModule
import org.deafsapps.android.cleanapp.presentationlayer.main.MainContract
import org.deafsapps.android.cleanapp.presentationlayer.main.view.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.koin.test.declareMock

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest : KoinTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        startKoin(listOf(presentationLayerModule))
        declareMock<MainContract.Presenter>()
        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun whenActivityStartsRecyclerViewIsDisplayed() {
        onView(withId(R.id.activity_main__rv__todo_items))
            .check(matches(isDisplayed()))
    }

}