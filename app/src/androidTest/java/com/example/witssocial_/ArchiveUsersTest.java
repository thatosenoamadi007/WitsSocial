
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
public class ArchiveUsersTest {
    @Rule
    public ActivityScenarioRule<Archive_Users> activityScenarioRule = new ActivityScenarioRule<>(Archive_Users.class);

    //checks if the back button is not nulll
    @Test
    public void GoBackToMessagesTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView go_back_to_chat = activity.findViewById(R.id.go_back_to_chat);
            assertNotNull(go_back_to_chat);
        });
    }

    //click the back button to go to previous activity
    @Test
    public void ClickGoBackToMessagesTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView go_back_to_chat = activity.findViewById(R.id.go_back_to_chat);
            go_back_to_chat.performClick();
        });
    }


}
