package com.example.simpleparadox.listycity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    // In our tests, the comment "new code" means that codes after that line are not copied from the previous tests
    private ActivityScenario<MainActivity> scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        scenario = activityRule.getScenario();
        Intents.init();
    }

    /*
    Add a city to the listView and check the city name
    Clear all the cities from the listView and check again
     */
    @Test
    public void checkList() {
        // finds the add button view, click the button, check if the button is displayed
        onView(withId(R.id.button_add)).check(matches(isDisplayed())).perform(click());
        // finds the edit text view, type something, check if the edit text is displayed
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        // finds the confirm button view, click the button, check if the button is displayed
        onView(withId(R.id.button_confirm)).check(matches(isDisplayed())).perform(click());
        // checks if "Edmonton" is in the list
        onData(hasEntry(equalTo(ListView.class), is("Edmonton")));
        // click clear all button
        onView(withId(R.id.button_clear)).check(matches(isDisplayed())).perform(click());
        // checks if data is still in the list
        onView(withId(R.id.city_list)).check(matches(not(hasDescendant(withText("Edmonton")))));    // can this derive into another way to check if item is in list?
    }

    /*
    Check item taken from the listView.
    Basically add a city to the list and get first item from the list, then check the name.
    Even though this part is in the test above, but we have to copy because JUnit test without clear order
     */
    @Test
    public void checkCityItem() {
        onView(withId(R.id.button_add)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editText_name)).check(matches(isDisplayed())).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).check(matches(isDisplayed())).perform(click());
        // new code
        onData(instanceOf(String.class)).atPosition(0).check(matches(withText("Edmonton")));
        // later on, if we want to check list item with multiple subviews, we may use
        // check - matches - hasDescendant - withText
    }

    @Test
    public void switchActivityTest() {
        onView(withId(R.id.button_add)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editText_name)).check(matches(isDisplayed())).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).check(matches(isDisplayed())).perform(click());
        onData(instanceOf(String.class)).atPosition(0).check(matches(withText("Edmonton")));
        // new code
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        intended(hasComponent(showActivity.class.getName()));   // checks if the new activity is the right one

    }

    @Test
    public void consistencyTest() {
        onView(withId(R.id.button_add)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editText_name)).check(matches(isDisplayed())).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).check(matches(isDisplayed())).perform(click());
        onData(instanceOf(String.class)).atPosition(0).check(matches(withText("Edmonton")));
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        intended(hasComponent(showActivity.class.getName()));
        // new code
        onView(withId(R.id.city_name)).check(matches(withText("Edmonton")));
    }

    // This code is used to get the current activity
    Activity currentActivity = null;

    public Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }

    @Test
    public void backButtonTest() {
        onView(withId(R.id.button_add)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editText_name)).check(matches(isDisplayed())).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).check(matches(isDisplayed())).perform(click());
        onData(instanceOf(String.class)).atPosition(0).check(matches(withText("Edmonton")));
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        intended(hasComponent(showActivity.class.getName()));
        onView(withId(R.id.city_name)).check(matches(withText("Edmonton")));
        onView(withId(R.id.back_button)).check(matches(isDisplayed())).perform(click());
        // new code
        Activity currentActivity = getActivityInstance();
        assertSame(currentActivity.getClass(), MainActivity.class);     // check if we went back to the correct activity
    }

    @After
    public void tearDown() {
        Intents.release();
        scenario.close();
    }
}