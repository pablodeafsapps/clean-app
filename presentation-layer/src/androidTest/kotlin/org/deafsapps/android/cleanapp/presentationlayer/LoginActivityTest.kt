package org.deafsapps.android.cleanapp.presentationlayer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.deafsapps.android.cleanapp.presentationlayer.login.view.ui.LoginActivity
import org.junit.Rule
import org.junit.Test

@LargeTest
class LoginActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun whenAppStartsLoginIsDisplayed() {
        onView(withId(R.id.activity_login_tv_title))
            .check(matches(isDisplayed()))
    }

    /*
    @Test
    fun editingDialogUpdatesTitle() {
        onView(withId(R.id.textVictoryTitle))
            .perform(click())

        val newTitle = "Made the bed"
        onView(instanceOf(EditText::class.java))
            .perform(clearText())
            .perform(typeText(newTitle))

        onView(withText(R.string.dialog_ok))
            .perform(click())

        onView(allOf(withId(R.id.textVictoryTitle), withText(newTitle)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun incrementingVictoryCountUpdatesCountView() {
        val previousCountString = rule.activity
            .findViewById(R.id.textVictoryCount).text.toString()
        val previousCount =
            if (previousCountString.isBlank()) {
                0
            } else {
                previousCountString.toInt()
            }

        onView(withId(R.id.fab))
            .perform(click())

        onView(
            allOf(
                withId(R.id.textVictoryCount),
                withText((previousCount + 1).toString())
            )
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun editingTitleDoesntChangeCount() {
        onView(withId(R.id.fab))
            .perform(click())

        onView(withId(R.id.textVictoryTitle))
            .perform(click())

        val newTitle = "Made the bed"
        onView(instanceOf(EditText::class.java))
            .perform(clearText())
            .perform(typeText(newTitle))

        onView(withText(R.string.dialog_ok))
            .perform(click())

        onView(allOf(withId(R.id.textVictoryCount), withText("0")))
            .check(doesNotExist())
    }
    */

}