package com.github.bkhezry.weather.ui.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.github.bkhezry.weather.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> myActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void mainActivityTest2() throws InterruptedException {
        ViewInteraction textView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search"),
                        withParent(withParent(withId(R.id.toolbar))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction materialSearchView = onView(
                allOf(withId(R.id.search_view),
                        childAtPosition(
                                allOf(withId(R.id.toolbarLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        materialSearchView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchTextView),
                        childAtPosition(
                                allOf(withId(R.id.search_top_bar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("timisoara"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.searchTextView), withText("timisoara"),
                        childAtPosition(
                                allOf(withId(R.id.search_top_bar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.animation_view),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(LinearLayout.class))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.next_days_button), withText("Next 15 Days"),
                        withParent(allOf(withId(R.id.coordinator_layout),
                                withParent(IsInstanceOf.<View>instanceOf(LinearLayout.class)))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.bar),
                                        childAtPosition(
                                                withId(R.id.coordinator_layout),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction switchCompat = onView(
                allOf(withId(R.id.night_mode_switch), withText("Night Mode"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_view),
                                        0),
                                1),
                        isDisplayed()));
        switchCompat.perform(click());
        Thread.sleep(2000);

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.night_mode_switch), withText("Night Mode"),
                        withParent(withParent(withId(R.id.card_view))),
                        isDisplayed()));
        switch_.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.close_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        1),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        Espresso.pressBackUnconditionally();
        new Activity();


        ViewInteraction textView3 = onView(
                allOf(withId(R.id.city_name_text_view), withText("Timișoara, RO"),
                        withParent(withParent(withId(R.id.toolbar))),
                        isDisplayed()));
        textView3.check(matches(withText("Timișoara, RO")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
