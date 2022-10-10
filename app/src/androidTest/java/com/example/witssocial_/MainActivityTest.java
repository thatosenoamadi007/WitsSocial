/*package com.example.witssocial_;

import static android.service.autofill.Validators.not;
import static android.support.test.InstrumentationRegistry.getInstrumentation;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mActivity = null;
    public String wrongDetails = "You are already registered, click the link above to login.";
    public String correctDetails = "Registered successfully";
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);

    @Test
    public void FullNameCheck(){
        EditText firstname = mActivity.findViewById(R.id.FullName);
        assertNotNull(firstname);
    }
    @Test
    public void Emailcheck(){
        EditText Email = mActivity.findViewById(R.id.Email);
        assertNotNull(Email);
    }
    @Test
    public void Password(){
        EditText Password = mActivity.findViewById(R.id.Password);
        assertNotNull(Password);
    }
    @Test
    public void ConPassword(){
        EditText ConnPassowrd = mActivity.findViewById(R.id.confirmPassword);
        assertNotNull(ConnPassowrd);
    }

    @Before
    public void setUp() throws Exception {
        mActivity = rActivityTestRule.getActivity();
    }

    @Test
    public void enterOldDetails(){
        Espresso.onView(withId(R.id.FullName)).perform(typeText("Neal")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirmPassword)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignUp)).perform(click());
        MainActivity activity = rActivityTestRule.getActivity();
        //Espresso.onView(withText(wrongDetails)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null ;
    }

}
*/
