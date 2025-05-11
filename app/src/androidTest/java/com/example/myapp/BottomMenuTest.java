package com.example.myapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;

public class BottomMenuTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBottomNavigation() {
        // Проверяем, что кнопка "Посты" отображается
        Espresso.onView(ViewMatchers.withId(R.id.postListFragment))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Нажимаем на кнопку "Посты" и проверяем, что она работает
        Espresso.onView(ViewMatchers.withId(R.id.postListFragment))
                .perform(ViewActions.click());

        // Проверяем, что кнопка "Избранное" отображается
        Espresso.onView(ViewMatchers.withId(R.id.favoritesFragment))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Нажимаем на "Избранное" и проверяем, что оно работает
        Espresso.onView(ViewMatchers.withId(R.id.favoritesFragment))
                .perform(ViewActions.click());
    }
}