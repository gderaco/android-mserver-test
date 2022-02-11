package com.example.exerciseforcandidates


import android.app.Application
import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnitRunner
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class TestApplication : AbstractApplication() {
    override val baseUrl = "http://10.0.2.2:1080"
}

class TestAndroidJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        val testApplicationClassName = TestApplication::class.java.name
        return super.newApplication(cl, testApplicationClassName, context)
    }
}

@LargeTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MainActivityTest {



    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.button),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.value),
            )
        )
        SystemClock.sleep(1000);
        textView.check(matches(not(withText(""))))
    }
}
