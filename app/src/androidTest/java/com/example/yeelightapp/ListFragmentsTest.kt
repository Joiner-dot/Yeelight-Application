package com.example.yeelightapp

import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.yeelightapp.lamps.LampDB
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.mapper.LampMapper
import com.example.yeelightapp.ui.LampActivity
import com.example.yeelightapp.ui.MainActivity
import com.example.yeelightapp.ui.fragments.AboutPage
import com.example.yeelightapp.ui.fragments.AddLamp
import com.example.yeelightapp.ui.fragments.ListFragments
import com.example.yeelightapp.ui.fragments.ManageStaticLight
import com.robotium.solo.Solo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.definition.Options

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
            navController.setGraph(R.navigation.my_nav)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.addButton))
            .perform(click())
        assertEquals(navController.currentDestination?.id, R.id.addLamp)
    }

    @Test(expected = NoMatchingViewException::class)
    fun lampNameButtonCheck() {
        val titleScenario = launchFragmentInContainer<ListFragments>()

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.my_nav)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.lampName))
            .perform(click())
        assertNotEquals(navController.currentDestination?.id, R.id.addLamp)
    }

    @Test
    fun testEvent() {
        val scenario = activityRule.scenario
        scenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun testRecreate() {
        val scenario = activityRule.scenario
        scenario.recreate()
    }

    @Test
    fun selectButtonCheck() {
        val titleScenario = launchFragmentInContainer<AddLamp>()

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.my_nav)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.select))
            .perform(click())
        assertEquals(navController.currentDestination?.id, R.id.listFragments)
    }
}