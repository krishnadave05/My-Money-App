package com.example.my_money_app;

import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
//ToDo: We need more tests for code coverage. Only at 16% right now.
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule loginActivityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginTest(){
        onView(withId(R.id.edit_email)).perform(replaceText("abc123@gmail.com"));
        onView(withId(R.id.edit_password)).perform(replaceText("abc123456"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.dashboard)).check(matches(withText("Dashboard")));
    }

    @Test
    public void isEmail() {
        onView(withId(R.id.edit_email)).perform(replaceText("abc123@gmail"));
        onView(withId(R.id.edit_password)).perform(replaceText("abc123456"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withText("abc123@gmail.com")).check(doesNotExist());
    }

    @Test
    public void emailIsEmpty() {
        onView(withId(R.id.edit_password)).perform(replaceText("abc123456"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withText("abc123@gmail.com")).check(doesNotExist());
    }

    @Test
    public void passwordIsEmpty() {
        onView(withId(R.id.edit_email)).perform(replaceText("abc123@gmail.com"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withText("abc123456")).check(doesNotExist());
    }
}