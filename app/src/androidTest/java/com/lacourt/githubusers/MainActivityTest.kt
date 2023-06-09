package com.lacourt.githubusers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.lacourt.githubusers.paging.UserListPageAdapter
import com.lacourt.githubusers.view.MainActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {
        // Simulate click on a specific item in the RecyclerView
        val position = 0
        onView(withId(R.id.rv_users_list))
            .perform(actionOnItemAtPosition<UserListPageAdapter.UserListViewHolder>(position, click()))
        Thread.sleep(2000)
        
        // Verify that the UserDetailsActivity is launched with the expected intent extras
        onView(withId(R.id.root_user_details))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSearchButtonClicked() {
        // Simulate click on the search button
        onView(withId(R.id.tv_search_users)).perform(click())
        Thread.sleep(2000)

        // Verify that the SearchActivity is launched
        onView(withId(R.id.root_search_activity))
            .check(matches(isDisplayed()))
    }
}