package com.example.myapp;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;

public class OpenPostTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testOpenPostDetails() {
        // Проверяем, что список постов отображается
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewPosts))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Нажимаем на первый пост
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewPosts))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // Проверяем, что `DetailFragment` загружен
        Espresso.onView(ViewMatchers.withId(R.id.textViewTitleDetail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}