
package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
public class a_FriendProfileTest {
    @Rule
    public ActivityScenarioRule<a_FriendProfile> activityScenarioRule = new ActivityScenarioRule<>(a_FriendProfile.class);

    //tests if the back button is not null
    @Test
    public void GotoEditProfileTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            de.hdodenhof.circleimageview.CircleImageView friend_profile = activity.findViewById(R.id.friend_profile);
            assertNotNull(friend_profile);
        });
    }

    //tests if the posts field is not null
    @Test
    public void PostsFieldTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView friend_profile_recyclerview = activity.findViewById(R.id.friend_profile_recyclerview);
            assertNotNull(friend_profile_recyclerview);
        });
    }

    //tests if the profile pic field is not null
    @Test
    public void ProfilePicTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            de.hdodenhof.circleimageview.CircleImageView friend_profile = activity.findViewById(R.id.friend_profile);
            assertNotNull(friend_profile);
        });
    }

    //tests if the email field is not null
    @Test
    public void EmailFieldTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView friend_email = activity.findViewById(R.id.friend_email);
            assertNotNull(friend_email);
        });
    }

    //tests if the description field is not null
    @Test
    public void DescriptionFieldTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView friend_description = activity.findViewById(R.id.friend_description);
            assertNotNull(friend_description);
        });
    }

    //tests if the email field is not null
    @Test
    public void NameFieldTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView friend_name = activity.findViewById(R.id.friend_name);
            assertNotNull(friend_name);
        });
    }

    //test the follow and unfollow button
    @Test
    public void FollowUnfollowFriendTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton follow_friend=activity.findViewById(R.id.follow_friend);
            assertNotNull(follow_friend);

            //follow friend
            follow_friend.performClick();

            //unfollow friend
            follow_friend.performClick();

        });
    }

}
