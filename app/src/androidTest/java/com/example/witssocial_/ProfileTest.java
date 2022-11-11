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
import android.widget.Button;
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

    //check if profile pic field is not null
    @Test
    public void CheckEditProfile(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton edit_my_profile = activity.findViewById(R.id.edit_my_profile);
            assertNotNull(edit_my_profile);
        });
    }

    //check if profile pic field is not null
    @Test
    public void CheckSignout(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatButton log_out_of_my_out = activity.findViewById(R.id.log_out_of_my_out);
            assertNotNull(log_out_of_my_out);
        });
    }

    //sign out user
    @Test
    public void Signout(){
        Espresso.onView(withId(R.id.log_out_of_my_out)).perform(click());
                /*.inRoot(isDialog())
                .check(matches(isDisplayed()));*/
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

}
