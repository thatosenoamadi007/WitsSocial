package com.example.witssocial_;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

import android.app.Instrumentation;
import android.content.Context;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.witssocial_", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<login> activityScenarioRule = new ActivityScenarioRule<>(login.class);
    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(login.class.getName(),null,false);

    @Test
    public void Emailcheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            EditText Email = activity.findViewById(R.id.Email);
            assertNotNull(Email);
        });

    }
    @Test
    public void Password(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            EditText Password = activity.findViewById(R.id.Password);
            assertNotNull(Password);
        });

    }
    /* @Before
     public void setUp() throws Exception {
        // mActivity = rActivityTestRule.getActivity();
     }*/
    @Test
    public void enterOldDetails(){
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignIn)).perform(click());
        //login activity = rActivityTestRule.getActivity();
        //Espresso.onView(withText(wrongDetails)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
