package com.example.yeelightapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.yeelightapp.ui.MainActivity
import com.example.yeelightapp.ui.fragments.ListFragments

@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun changeText_sameActivity() {
        launchFragmentInContainer<ListFragments>()
        onView(withId(R.id.textView2))
            .check(matches(withText("Choose A Lamp")))
    }


    @Test
    fun floatingButtonCheck() {

        val titleScenario = launchFragmentInContainer<ListFragments>()

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.my_nav)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.addButton))
            .perform(click())
        assertEquals(navController.currentDestination?.id, R.id.addLamp)

    }
}