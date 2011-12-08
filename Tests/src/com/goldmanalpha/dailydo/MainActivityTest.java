package com.goldmanalpha.dailydo;

import android.test.ActivityInstrumentationTestCase2;
import com.goldmanalpha.dailydo.model.DoableBase;
import com.goldmanalpha.dailydo.model.DoableItem;

import java.util.Date;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.goldmanalpha.dailydo.MainActivityTest \
 * com.goldmanalpha.dailydo.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super("com.goldmanalpha.dailydo", MainActivity.class);
    }

}
