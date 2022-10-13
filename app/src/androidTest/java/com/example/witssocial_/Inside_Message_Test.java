package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatEditText;
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
public class Inside_Message_Test{
    @Rule
    public ActivityScenarioRule<InsideMessage> activityScenarioRule = new ActivityScenarioRule<>(InsideMessage.class);
    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(InsideMessage.class.getName(),null,false);

    @Test
    public void messageCheck(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            AppCompatEditText typedMessage = activity.findViewById(R.id.message_to_send_InsideMessage);

            assertNotNull(typedMessage);
        });

    }


    @Test
    public void enterMessage(){
        Espresso.onView(withId(R.id.message_to_send_InsideMessage)).perform(typeText("A Message")).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.send_message_InsideMessage)).perform(click());
    }

}
