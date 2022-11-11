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
public class loginTest {
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
    @Test
    public void enterWrongDetails(){
        Espresso.onView(withId(R.id.Email)).perform(typeText("nealneal1@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("123")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignIn)).perform(click());
    }
    @Test
    public void enterCorrectDetails(){
        Espresso.onView(withId(R.id.Email)).perform(typeText("karabol@gmail.com")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.Password)).perform(typeText("123456")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.SignIn)).perform(click());
    }


}
