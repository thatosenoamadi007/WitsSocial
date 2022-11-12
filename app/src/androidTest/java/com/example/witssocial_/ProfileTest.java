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
public class ProfileTest {
    @Rule
    public ActivityScenarioRule<Profile> activityScenarioRule = new ActivityScenarioRule<>(Profile.class);

    //check if profile pic field is not null
    @Test
    public void CheckUserProfilePic(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            de.hdodenhof.circleimageview.CircleImageView userprofilepic = activity.findViewById(R.id.userprofile);
            assertNotNull(userprofilepic);
        });
    }

    //check if edit profile button is not null
    @Test
    public void CheckEditProfile(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton edit_my_profile = activity.findViewById(R.id.edit_my_profile);
            assertNotNull(edit_my_profile);
        });
    }

    //check if sign out buttin is not null
    @Test
    public void CheckSignout(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton log_out_of_my_out = activity.findViewById(R.id.log_out_of_my_out);
            assertNotNull(log_out_of_my_out);
            log_out_of_my_out.performClick();
        });
    }

    //check if the email field is not null
    @Test
    public void CheckEmail(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView my_email = activity.findViewById(R.id.my_email);
            assertNotNull(my_email);
        });
    }

    //check if the username field is not null
    @Test
    public void CheckUsername(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView my_username = activity.findViewById(R.id.my_username);
            assertNotNull(my_username);
        });
    }

    //check if the description field is not null
    @Test
    public void CheckDescription(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView my_profile_description = activity.findViewById(R.id.my_profile_description);
            assertNotNull(my_profile_description);
        });
    }

    //check if the posts field is not null
    @Test
    public void CheckPosts(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView my_account_profile_recyclerview = activity.findViewById(R.id.my_account_profile_recyclerview);
            assertNotNull(my_account_profile_recyclerview);
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

            //go back to account activity
            /*bottomNavigationView.findViewById(R.id.account)
                    .performClick();*/
        });

        //go to add post activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the add post activity icon
            bottomNavigationView.findViewById(R.id.add_post)
                    .performClick();

            //go back to account activity
            /*bottomNavigationView.findViewById(R.id.account)
                    .performClick();*/
        });

        //go to search users activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the search users activity icon
            bottomNavigationView.findViewById(R.id.chat)
                    .performClick();

            //go back to account activity
            /*bottomNavigationView.findViewById(R.id.account)
                    .performClick();*/
        });

        //go to list of chats activity from navigation bar
        activityScenarioRule.getScenario().onActivity(activity -> {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

            //click on the messages activity
            bottomNavigationView.findViewById(R.id.messages)
                    .performClick();

            //go back to account activity
            /*bottomNavigationView.findViewById(R.id.account)
                    .performClick();*/
        });

    }





}
