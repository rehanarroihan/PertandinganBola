package com.multazamgsd.pertandinganbola

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.multazamgsd.pertandinganbola.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onCreate() {
        Thread.sleep(2000)
        Espresso.onView(withId(match)).perform(click())
        Thread.sleep(2000)
        Espresso.onView(withId(club)).perform(click())
        Thread.sleep(2000)
        Espresso.onView(withId(match)).perform(click())

        Espresso.onView(withId(spinnerMatch)).check(matches(isDisplayed()))
        Espresso.onView(withId(spinnerMatch)).perform(click())
        Espresso.onView(withText("Spanish La Liga")).perform(click())

        Thread.sleep(2000)
        Espresso.onView(withId(btNext)).perform(click())

        Thread.sleep(2000)
        Espresso.onView(withId(recyclerViewMatch))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(recyclerViewMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(withId(recyclerViewMatch)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(2000)
    }
}