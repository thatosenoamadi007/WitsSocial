package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static junit.framework.TestCase.assertNotNull;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    @Test
    public void FullNameCheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            EditText firstname = activity.findViewById(R.id.FullName);
            assertNotNull(firstname);
        });
    }
    
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
    @Test
    public void ConPassword(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            EditText ConnPassowrd = activity.findViewById(R.id.confirmPassword);
            assertNotNull(ConnPassowrd);
        });

    }
    @Test
    public void enterWrongDetails(){
        Espresso.onView(withId(R.id.FullName)).perform(typeText("Neal")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal1@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirmPassword)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignUp)).perform(click());
    }
    @Test
    public void enterCorrectDetails(){
        Espresso.onView(withId(R.id.FullName)).perform(typeText("Neal Beck")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal1@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirmPassword)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignUp)).perform(click());
    }

    @Test
    public void enterExistingDetails(){
        Espresso.onView(withId(R.id.FullName)).perform(typeText("Neal Beck")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirmPassword)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignUp)).perform(click());
    }

}
