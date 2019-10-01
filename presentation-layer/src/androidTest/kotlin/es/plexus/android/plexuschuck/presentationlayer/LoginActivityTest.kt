package es.plexus.android.plexuschuck.presentationlayer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import es.plexus.android.plexuschuck.presentationlayer.di.presentationLayerModule
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.ui.LoginActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest : KoinTest {

    @get:Rule
    var activityRule = ActivityTestRule(LoginActivity::class.java, false, false)

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(presentationLayerModule))
        }
        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun whenActivityStartsLoginIsDisplayed() {
        onView(withId(R.id.activity_login_tv_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenActivityStartsAndRegisterIsTappedRegisterTitleIsDisplayed() {
        val requiredText = "Register"

        onView(withId(R.id.activity_login__tb__access_mode))
            .perform(click())

        onView(withId(R.id.activity_login_tv_title))
            .check(matches(withText(requiredText)))
    }

}