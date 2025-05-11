package com.example.myapp;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSearchViewInput() {
        Espresso.onView(ViewMatchers.withId(R.id.searchView))
                .perform(ViewActions.typeText("Пример поста"));
        Espresso.onView(ViewMatchers.withId(R.id.searchView))
                .check(matches(ViewMatchers.withSubstring("Пример поста")));
    }
}