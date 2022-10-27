package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
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
public class InsideMessageTest {
    @Rule
    public ActivityScenarioRule<InsideMessage> activityScenarioRule = new ActivityScenarioRule<>(InsideMessage.class);

    //display list of chats and check is not null
    @Test
    public void ShowChatHistory(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView messages_insidemessage = activity.findViewById(R.id.messages_insidemessage);
            assertNotNull(messages_insidemessage);
        });
    }

    //check if the enter message field is not null
    @Test
    public void EnterMessage(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatEditText message_to_send_InsideMessage = activity.findViewById(R.id.message_to_send_InsideMessage);
            assertNotNull(message_to_send_InsideMessage);
        });
    }

    //check if back button is not null and is working
    @Test
    public void GoBackButton(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatImageView go_back_insidemessage = activity.findViewById(R.id.go_back_insidemessage);
            assertNotNull(go_back_insidemessage);
        });
    }


}
