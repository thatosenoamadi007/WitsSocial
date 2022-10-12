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
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class home_activityTest {
    @Rule
    public ActivityScenarioRule<home_activity> activityScenarioRule = new ActivityScenarioRule<>(home_activity.class);
    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(home_activity.class.getName(),null,false);

    @Test
    public void AllPostscheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            TextView All_Posts = activity.findViewById(R.id.All_Posts);
            assertNotNull(All_Posts);
        });

    }
    @Test
    public void Media_PostsCheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            TextView Media_Posts = activity.findViewById(R.id.Media_Posts);
            assertNotNull(Media_Posts);
        });

    }
    @Test
    public void Text_PostsCheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            TextView Text_Posts = activity.findViewById(R.id.Text_Posts);
            assertNotNull(Text_Posts);
        });

    }
    @Test
    public void AllPostsClick(){
        Espresso.onView(withId(R.id.All_Posts)).perform(click());
    }

    @Test
    public void Media_PostsClick(){
        Espresso.onView(withId(R.id.Media_Posts)).perform(click());
    }

    @Test
    public void Text_PostsClick(){
        Espresso.onView(withId(R.id.Text_Posts)).perform(click());
    }
}