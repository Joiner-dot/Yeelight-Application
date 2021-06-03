package com.example.yeelightapp


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.yeelightapp.ui.MainActivity
import com.example.yeelightapp.ui.fragments.AddLamp
import com.example.yeelightapp.ui.fragments.ListFragments
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


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
    fun currentDistTest() {
        val titleScenario = launchFragmentInContainer<ListFragments>()
        titleScenario.onFragment { fragment ->
            assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            navController.setGraph(R.navigation.my_nav)
            val currentDestination = navController.currentDestination
            assertEquals(R.id.listFragments, currentDestination?.id)
        }
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
            assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread { navController.setGraph(R.navigation.my_nav) }
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(R.id.action_listFragments_to_addLamp)
            var currentDestination = navController.currentDestination
            assertEquals(currentDestination?.id, R.id.addLamp)
            navController.navigate(R.id.action_addLamp_to_listFragments)
            currentDestination = navController.currentDestination
            assertEquals(currentDestination?.id, R.id.listFragments)
        }
    }
}