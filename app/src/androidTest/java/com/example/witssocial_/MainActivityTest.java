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
/*package com.example.witssocial_;

import junit.framework.TestCase;

public class MainActivityTest extends TestCase {

    public void testSetPost() {
        Post post=new Post();
        post.setPost("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }

    public void testGetPost() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png","Top of the morning","karabo@gmail.com","my id");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }

    public void testSetCaption() {
        Post post=new Post();
        post.setCaption("Top of the morning");
        assertEquals("Top of the morning",post.getCaption());
    }

    public void testGetCaption() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id");
        assertEquals("Top of the morning",post.getCaption());
    }

    public void testSetUsername() {
        Post post=new Post();
        post.setUsername("karabo@gmail.com");
        assertEquals("karabo@gmail.com",post.getUsername());
    }

    public void testGetUsername() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id");
        assertEquals("karabo@gmail.com",post.getUsername());
    }
}*/
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
    private login mActivity = null;
    public String wrongDetails = "You are already registered, click the link above to login.";
    public String correctDetails = "Registered successfully";
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
    /* @Before
     public void setUp() throws Exception {
        // mActivity = rActivityTestRule.getActivity();
     }*/
    @Test
    public void enterWrongDetails(){
        Espresso.onView(withId(R.id.FullName)).perform(typeText("Neal")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal1@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirmPassword)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignUp)).perform(click());
        //login activity = rActivityTestRule.getActivity();
        //Espresso.onView(withText(wrongDetails)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void enterCorrectDetails(){
        Espresso.onView(withId(R.id.FullName)).perform(typeText("Neal Beck")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal1@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirmPassword)).perform(typeText("1234567")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignUp)).perform(click());
        //login activity = rActivityTestRule.getActivity();
        //Espresso.onView(withText(wrongDetails)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    /*@After
    public void tearDown() throws Exception {
        mActivity = null ;
    }*/
}
