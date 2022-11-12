package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.recyclerview.widget.RecyclerView;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    //check if the posts field is not null
    @Test
    public void Text_PostsCheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView Text_Posts = activity.findViewById(R.id.homerecview);
            assertNotNull(Text_Posts);

            //like post
            //Text_Posts.findViewById(R.id.heart).performClick();
            
        });
    }

    //test the bottom navigation bar
    @Test
    public void CheckNavigationBar(){

        //go to posts activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the posts icon activity
            bottomNavigationView.findViewById(R.id.account)
                    .performClick();

        });

        //go to add post activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the add post activity icon
            bottomNavigationView.findViewById(R.id.add_post)
                    .performClick();

        });

        //go to search users activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the search users activity icon
            bottomNavigationView.findViewById(R.id.chat)
                    .performClick();

        });

        //go to list of chats activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the messages activity
            bottomNavigationView.findViewById(R.id.messages)
                    .performClick();

        });

    }

}
