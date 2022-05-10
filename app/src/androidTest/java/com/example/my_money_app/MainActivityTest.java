package com.example.my_money_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void displayText(){
        onView(withId(R.id.Hello_world)).check(matches(withText("Hello world!")));
    }
}
