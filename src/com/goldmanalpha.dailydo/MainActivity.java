package com.goldmanalpha.dailydo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.com.goldmanalpha.dailydo.db.DoableItemValueTableAdapter;
import com.goldmanalpha.androidutility.DayOnlyDate;
import com.goldmanalpha.androidutility.EnumHelper;
import com.goldmanalpha.androidutility.PickOneList;
import com.goldmanalpha.dailydo.model.DoableBase;
import com.goldmanalpha.dailydo.model.DoableValue;
import com.goldmanalpha.dailydo.model.TeaSpoons;
import com.goldmanalpha.dailydo.model.UnitType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    private TextView mDateDisplay;
    Date mDisplayingDate;
    DoableItemValueTableAdapter doableItemValueTableAdapter;

    private static final SimpleDateFormat shortMonthDateFormat = new SimpleDateFormat("MMM-dd");


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        DoableBase.setContext(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);

        updateDisplayDate(new DayOnlyDate());

    }

    boolean setupDate = false;
    SimpleCursorAdapter listCursorAdapter;

    private void SetupList2(Date date) {
        cachedCursor = doableItemValueTableAdapter.getItems(date);
        startManagingCursor(cachedCursor);

        listCursorAdapter.changeCursor(cachedCursor);
    }

    ListView myList;
    Cursor cachedCursor;
    int valueIdColumnIndex;
    int itemIdColumnIndex;

    int teaspoonColIdx;
    int lastTeaspoonColIdx;
    int unitTypeColIdx;
    final String usesTeaspoonsType = UnitType.tsp.toString();

    private void SetupList(Date date) {

        if (setupDate) {
            SetupList2(date);
            return;
        }

        setupDate = true;

        doableItemValueTableAdapter = new DoableItemValueTableAdapter(this);
        cachedCursor = doableItemValueTableAdapter.getItems(date);

        valueIdColumnIndex = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColId);
        itemIdColumnIndex = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColItemId);

        startManagingCursor(cachedCursor);

        String[] from = new String[]{DoableItemValueTableAdapter.ColItemName,
                DoableItemValueTableAdapter.ColUnitType,
                DoableItemValueTableAdapter.ColAmount,
                DoableItemValueTableAdapter.ColTeaspoons,
                DoableItemValueTableAdapter.ColLastAppliesToDate,
                DoableItemValueTableAdapter.ColLastAmount,
                DoableItemValueTableAdapter.ColLastTeaspoons
        };

        int[] to = new int[]{R.id.list_name, R.id.list_unit_type,
                R.id.amount, R.id.list_teaspoons,
                R.id.list_lastDate, R.id.list_lastAmount,
                R.id.list_lastTeaspoons
        };

        teaspoonColIdx = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColTeaspoons);
        lastTeaspoonColIdx = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColLastTeaspoons);
        unitTypeColIdx = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColUnitType);
        final int lastAppliesToDateColIdx = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColLastAppliesToDate);

        final int lastTeaspoonsColIdx = cachedCursor.getColumnIndex(DoableItemValueTableAdapter.ColLastTeaspoons);



        myList = (ListView) findViewById(R.id.main_list);

        listCursorAdapter = new SimpleCursorAdapter(myList.getContext(),
                R.layout.main_list_item, cachedCursor, from, to);

        myList.setAdapter(listCursorAdapter);

        listCursorAdapter.setViewBinder(
                new SimpleCursorAdapter.ViewBinder() {
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {


                        if (columnIndex == teaspoonColIdx) {
                            TextView tv = ((TextView) view);

                            if (!IsTeaspoons(cursor)) {
                                tv.setText("");
                                return true;
                            } else {

                                tv.setText(getTeaspoonsForCursorPosition(cursor));
                                return true;
                            }
                        }

                        if (columnIndex == lastTeaspoonsColIdx) {
                            TextView tv = ((TextView) view);

                            if (!IsTeaspoons(cursor)) {
                                tv.setText("");
                                return true;
                            }
                        }

                        if (columnIndex == lastAppliesToDateColIdx) {
                            //format the date

                            String lastAppliesToDate = cursor.getString(columnIndex);

                            if (lastAppliesToDate == null)
                            {
                                return true;
                            }

                            try {
                                Date d = DoableItemValueTableAdapter.simpleDateFormat.parse(lastAppliesToDate);

                                TextView tv = (TextView) view;

                                tv.setText(shortMonthDateFormat.format(d));

                                return true;

                            } catch (ParseException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }

                        return false;
                    }
                }
        );

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (view instanceof TextView) {

                    // When clicked, show a toast with the TextView text
                    Toast.makeText(getApplicationContext(),
                            ((TextView) view).getText(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    boolean IsTeaspoons(Cursor cursor) {

        return cursor.getString(unitTypeColIdx).equals(usesTeaspoonsType);
    }

    public final static TeaSpoons defaultTeaspoons = TeaSpoons.eighth;

    class ValueIdentifier {
        public int ValueId;
        public int ItemId;

        @Override
        public String toString() {

            return "ItemId: " + ItemId + " ValueId: " + ValueId;
        }
    }

    //beneficial side effect - sets cachedCursor to proper position
    public ValueIdentifier GetValueIds(View view) {
        if (cachedCursor.moveToPosition(myList.getPositionForView(view))) {

            ValueIdentifier vi = new ValueIdentifier();

            vi.ValueId = cachedCursor.getInt(valueIdColumnIndex);
            vi.ItemId = cachedCursor.getInt(itemIdColumnIndex);

            return vi;
        }

        return null;
    }

    public void nameClick(View view) {
        ValueIdentifier ids = GetValueIds(view);

        Toast.makeText(getApplicationContext(),
                ids.toString() + " " + ((TextView) view).getText(),
                Toast.LENGTH_SHORT).show();

    }


    public void unit_type_click(View v) {
        ValueIdentifier ids = GetValueIds(v);

        Toast.makeText(getApplicationContext(),
                ids.toString() + " " + ((TextView) v).getText(),
                Toast.LENGTH_SHORT).show();

    }


    int teaspoonsClickValueId;

    public void teaspoons_click(View v) {

        teaspoonsClickValueId = this.GetValueIds(v).ValueId;

        Intent intent = new Intent(this, PickOneList.class);

        intent.putExtra(PickOneList.Title, "Pick Unit Teaspoon Size");

        intent.putExtra(PickOneList.SelectedItem, ((TextView) v).getText());

        intent.putExtra(PickOneList.Choices,
                EnumHelper.EnumNameToStringArray(TeaSpoons.values(), 1));


        startActivityForResult(intent, IntentRequestCodes.TeaspoonSelection);
    }

    class IntentRequestCodes {
        public static final int TeaspoonSelection = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.


        if (resultCode == RESULT_OK &&
                requestCode == IntentRequestCodes.TeaspoonSelection
                ) {
            try {

                String setToTeaspoons = data.getStringExtra(PickOneList.SelectedItem);

                DoableValue value = doableItemValueTableAdapter
                        .get(teaspoonsClickValueId);


                if (!value.getTeaspoons().toString().equals(setToTeaspoons)) {
                    value.setTeaspoons(TeaSpoons.valueOf(setToTeaspoons));

                    doableItemValueTableAdapter.save(value);
                }

                SetupList(new DayOnlyDate(this.mDisplayingDate));

            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                Toast.makeText(this, "Error saving tsp: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }


    }


    String getTeaspoonsForCursorPosition(Cursor c) {


        String setTeaspoons = c.getString(this.teaspoonColIdx);
        String lastTeaspoons = c.getString(this.lastTeaspoonColIdx);

        String unset = TeaSpoons.unset.toString();

        if (setTeaspoons == null || unset.equals(setTeaspoons)) {
            if (lastTeaspoons != null && !unset.equals(lastTeaspoons)) {
                return lastTeaspoons;

            }
        } else {
            return setTeaspoons;
        }


        return defaultTeaspoons.toString();

    }

    public void add_click(View v) throws ParseException {
        TextView tv = (TextView) v;

        int addAmount = 0;

        switch (tv.getId()) {
            case R.id.big_minus:
                addAmount = -5;
                break;
            case R.id.big_plus:
                addAmount = +5;
                break;
            case R.id.plus:
                addAmount = 1;
                break;
            case R.id.minus:
                addAmount = -1;
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Unexpected source for add_click", Toast.LENGTH_LONG)
                        .show();
        }

        if (addAmount != 0) {
            ValueIdentifier ids = GetValueIds(v);

            DoableValue value = doableItemValueTableAdapter
                    .get(ids.ValueId);

            //its a new value:
            if (value.getDoableItemId() == 0) {
                value.setDoableItemId(ids.ItemId);
            }

            //if its tsp, make sure there's a tsp type set
            if (value.getTeaspoons() == TeaSpoons.unset && IsTeaspoons(cachedCursor)) {
                value.setTeaspoons(
                        TeaSpoons.valueOf(
                        this.getTeaspoonsForCursorPosition(cachedCursor)));
            }


            if (value.getId() == 0)
            {
                //its a new value, start with last value used
                value.setAmount(cachedCursor.getFloat(cachedCursor.getColumnIndex(
                        DoableItemValueTableAdapter.ColLastAmount)));
            }
            else
            {
                value.setAmount(value.getAmount() + addAmount);
            }

            value.setAppliesToDate(this.mDisplayingDate);


            doableItemValueTableAdapter.save(value);


            cachedCursor.requery();
        }


    }

    public void nextDayClick(View v) {
        updateDisplayDate(addDays(mDisplayingDate, 1));

    }


    public void prevDayClick(View v) {
        updateDisplayDate(addDays(mDisplayingDate, -1));

    }

    public void addItemClick(View v) {
        startActivity(new Intent(this, AddItemActivity.class));
    }

    private void updateDisplayDate(Date date) {

        mDisplayingDate = date;
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
        mDateDisplay.setText(format.format(date));

        SetupList(new DayOnlyDate(date));
    }

    Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);  // number of days to add
        return c.getTime();  // dt is now the new date
    }


}
