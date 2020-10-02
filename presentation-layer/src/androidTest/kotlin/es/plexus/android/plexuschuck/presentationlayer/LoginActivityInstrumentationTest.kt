package es.plexus.android.plexuschuck.presentationlayer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import es.plexus.android.plexuschuck.domainlayer.di.domainLayerModule
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_BRIDGE_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.di.presentationLayerModule
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.ui.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentationTest : KoinTest {

    @get:Rule
    var activityRule = ActivityTestRule(LoginActivity::class.java, false, false)

    private lateinit var mockBridge: LoginDomainLayerBridge<UserLoginBo, Boolean>

    @Before
    fun setUp() {
        mockBridge = mock()
        startKoin {
            modules(listOf(
                presentationLayerModule, domainLayerModule,
                module(override = true) {
                    factory(named(name = LOGIN_DOMAIN_BRIDGE_TAG)) { mockBridge }
                }
            ))
        }
        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun whenActivityStartsLoginIsDisplayed() {
        onView(withId(R.id.tvTitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenActivityStartsAndRegisterIsTappedRegisterTitleIsDisplayed() {
        val requiredText = "Register"
        onView(withId(R.id.tbAccessMode))
            .perform(click())
        onView(withId(R.id.tvTitle))
            .check(matches(withText(requiredText)))
    }

    @Test
    fun whenEditTextsAreFilledUpLoginButtonIsDisplayed() {
        val requiredEmail = "pablo@mytest.es"
        val requiredPassword = "pablomytest"

        onView(withId(R.id.etEmail))
            .perform(clearText(), typeText(requiredEmail))
        onView(withId(R.id.etPassword))
            .perform(clearText(), typeText(requiredPassword))
        onView(withId(R.id.btnLogin))
            .check(matches(isDisplayed()))
    }

}