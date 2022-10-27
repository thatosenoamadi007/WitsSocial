package com.example.witssocial_;
import static android.service.autofill.Validators.not;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
public class add_postTest {
    @Rule
    public ActivityScenarioRule<add_post> activityScenarioRule = new ActivityScenarioRule<>(add_post.class);

    //check if the post button is not nul
    @Test
    public void PostButton(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            Button postBtn = activity.findViewById(R.id.postBtn);
            assertNotNull(postBtn);
        });
    }

    //check if the caption field is not nul
    @Test
    public void Caption(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            EditText makecaption = activity.findViewById(R.id.makecaption);
            assertNotNull(makecaption);
        });
    }

    //try to upload post and check if the post was uploaded
    @Test
    public void SubmitPost(){
        Espresso.onView(withId(R.id.makecaption)).perform(typeText("karabol@gmail.com")).perform(closeSoftKeyboard());
    }

}
