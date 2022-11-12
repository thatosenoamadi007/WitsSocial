package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
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
public class add_PostTest {
    @Rule
    public ActivityScenarioRule<add_post> activityScenarioRule = new ActivityScenarioRule<>(add_post.class);

    //check if caption field is not null
    @Test
    public void CheckCaption(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            TextView caption = activity.findViewById(R.id.makecaption);
            assertNotNull(caption);
        });
    }

    //type caption or text post and submit
    @Test
    public void TypeCaption(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            //type caption
            TextView caption = activity.findViewById(R.id.makecaption);
            caption.setText("Whats good!.");

            //upload post
            Button postBtn = activity.findViewById(R.id.postBtn);
            postBtn.performClick();

        });
    }

    //testing the navigation bar

    //test the bottom navigation bar
    @Test
    public void CheckNavigationBar(){

        //go to posts activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the posts icon activity
            bottomNavigationView.findViewById(R.id.posts_timeline)
                    .performClick();

        });

        //go to add post activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the add post activity icon
            bottomNavigationView.findViewById(R.id.account)
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
