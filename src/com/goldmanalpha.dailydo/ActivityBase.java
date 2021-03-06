package com.goldmanalpha.dailydo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.goldmanalpha.androidutility.DayOnlyDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityBase extends Activity {

    protected TextView customTitleText;
    protected WindowState currentWindowState = WindowState.DEFAULT;
    private ViewGroup viewRoot;
    protected String LogTag = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.title_bar);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        TextView customTitleText = (TextView) findViewById(R.id.title_bar_text_view);
        TextView rightTitle = (TextView) findViewById(R.id.title_bar_right_text_view);
        viewRoot = (ViewGroup) findViewById(R.id.title_bar_layout_root);

        customTitleText.setText(this.getString(R.string.app_name));

        rightTitle.setText(RightTitle());
        setWindowState(WindowState.DEFAULT);
    }

    protected String RightTitle()
    {
        return "";
    }

    protected void setWindowState(Date date)
    {
        DayOnlyDate today = new DayOnlyDate();
        DayOnlyDate inputDay = new DayOnlyDate(date);
        if (new DayOnlyDate().equals(inputDay))
        {
            setWindowState(WindowState.TODAY);
        }
        else if(addDays(today, -1).equals(inputDay))
        {
            setWindowState(WindowState.YESTERDAY);
        }
        else
        {
            setWindowState(WindowState.OUT_OF_RANGE);
        }
    }


    //todo: move to dateHelper:
    protected Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);  // number of days to add
        return c.getTime();  // dt is now the new date
    }

    protected String DateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
        return format.format(date);
    }


    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        setWindowState(lastState);
    }

    private WindowState lastState = WindowState.DEFAULT;

    protected WindowState getLastWindowState()
    {
        return lastState;
    }

    protected void setWindowState(WindowState state) {

        lastState = state;
        int color = Color.BLACK;

        switch (state) {
            case TODAY:
                color = Color.GREEN;
                break;
            case YESTERDAY:
                color = Color.CYAN;
                break;
            case OUT_OF_RANGE:
                color = Color.RED;
                break;
        }

        viewRoot.setBackgroundColor(color);
    }

    public enum WindowState {
        DEFAULT,
        TODAY,
        YESTERDAY,
        OUT_OF_RANGE
    }
}
