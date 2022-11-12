
package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
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

    @Test
    public void GotoEditProfileClick(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            de.hdodenhof.circleimageview.CircleImageView friend_profile = activity.findViewById(R.id.friend_profile);
            assertNotNull(friend_profile);
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
