package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatImageView;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class FollowingTest {
    @Rule
    public ActivityScenarioRule<Following> activityScenarioRule = new ActivityScenarioRule<>(Following.class);

    //checks if the back button is not null
    @Test
    public void BackButtonTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView go_back_to_my_profile = activity.findViewById(R.id.go_back_to_my_profile);
            assertNotNull(go_back_to_my_profile);
        });
    }

    //checks if following list field is not null
    @Test
    public void FollowingListTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView show_list_of_following = activity.findViewById(R.id.show_list_of_following);
            assertNotNull(show_list_of_following);
        });
    }

}
