
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

    //display friend profile pic
    @Test
    public void ProfilePic(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            de.hdodenhof.circleimageview.CircleImageView friend_profile = activity.findViewById(R.id.friend_profile);
            assertNotNull(friend_profile);
        });
    }

    //list friend posts and check if it is not null
    @Test
    public void ListPosts(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView list_posts = activity.findViewById(R.id.friend_profile_recyclerview);
            assertNotNull(list_posts);
        });
    }

    @Test
    public void FollowUsers(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton follow_friend = activity.findViewById(R.id.follow_friend);
            assertNotNull(follow_friend);
        });
    }

    //check if the message button is working
    @Test
    public void Message(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton message_friend = activity.findViewById(R.id.message_friend);
            assertNotNull(message_friend);
        });
    }

    //check if friend details are displayed
    @Test
    public void FriendDetails(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView friend_email = activity.findViewById(R.id.friend_email);
            AppCompatTextView friend_name = activity.findViewById(R.id.friend_name);
            AppCompatTextView friend_description = activity.findViewById(R.id.friend_description);
            assertNotNull(friend_email);
            assertNotNull(friend_name);
            assertNotNull(friend_description);
        });
    }

    //show list of followers or following test
    @Test
    public void NumberOfFollowers(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatTextView number_of_followers = activity.findViewById(R.id.number_of_followers);
            AppCompatTextView number_of_following = activity.findViewById(R.id.number_of_following);
            assertNotNull(number_of_followers);
            assertNotNull(number_of_followers);
        });
    }



}
