package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
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
public class ListOfChats {
    @Rule
    public ActivityScenarioRule<Messages> activityScenarioRule = new ActivityScenarioRule<>(Messages.class);

    //display list of chats and check is not null
    @Test
    public void MyChatList(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView all_friends_chat_list = activity.findViewById(R.id.all_friends_chat_list);
            assertNotNull(all_friends_chat_list);
        });
    }

    //check if the archive button is not null
    @Test
    public void ArchiveButton(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            de.hdodenhof.circleimageview.CircleImageView archived_icon = activity.findViewById(R.id.archived_icon);
            assertNotNull(archived_icon);
        });
    }


}
