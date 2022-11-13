
package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
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
public class commentSectionTest {
    @Rule
    public ActivityScenarioRule<Comment_Section> activityScenarioRule = new ActivityScenarioRule<>(Comment_Section.class);

    //tests if the back button is not null
    @Test
    public void BackButtonTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView go_back_to_home_activity = activity.findViewById(R.id.go_back_to_home_activity);
            assertNotNull(go_back_to_home_activity);
        });
    }

    //clicks the back button and checks if it behaves as expected
    @Test
    public void ClickBackButtonTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView go_back_to_home_activity = activity.findViewById(R.id.go_back_to_home_activity);
            assertNotNull(go_back_to_home_activity);
            go_back_to_home_activity.performClick();
        });
    }

    //tests if the submit comment button is not null
    @Test
    public void SubmitButtonTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView upload_comment = activity.findViewById(R.id.upload_comment);
            assertNotNull(upload_comment);
        });
    }

    //tests if the type comment field is not null
    @Test
    public void TypeCommentTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatEditText add_a_comment = activity.findViewById(R.id.add_a_comment);
            assertNotNull(add_a_comment);
        });
    }

    //type and submit comment test
    @Test
    public void TypeSubmitTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatEditText add_a_comment = activity.findViewById(R.id.add_a_comment);
            AppCompatImageView upload_comment = activity.findViewById(R.id.upload_comment);

            //types comment
            add_a_comment.setText("That's lit man");

            //upload comment
            upload_comment.performClick();

            assertEquals("That's lit man",add_a_comment.getText().toString());

        });
    }





}
