package com.goldmanalpha.dailydo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.com.goldmanalpha.dailydo.db.DoableItemValueTable;
import com.com.goldmanalpha.dailydo.db.DoableItemValueTableAdapter;
import com.goldmanalpha.dailydo.model.DoableItem;
import com.goldmanalpha.dailydo.model.DoableValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Aaron
 * Date: 1/2/12
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditDescriptionActivity extends ActivityBase{

    DoableItemValueTableAdapter tableAdapter;
    DoableValue value;
    public static final String ExtraValueId = "valueId";
    public static final String ExtraValueOutOfRangeDateOK = "ExtraValueOutOfRangeDateOK";

    boolean outOfRangeDateOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.


        setContentView(R.layout.edit_description);
        tableAdapter = new DoableItemValueTableAdapter();

        Intent intent = getIntent();
        int valueId = intent.getIntExtra(ExtraValueId, 0);
        outOfRangeDateOK = intent.getBooleanExtra(ExtraValueOutOfRangeDateOK, false);

        try {
            value = tableAdapter.get(valueId);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            this.finish();
        }

        this.date = new SimpleDateFormat("EEE, MMM d, yyyy").format(value.getAppliesToDate());
        setWindowState(value.getAppliesToDate());

        setTitle(this.date + ": " + value.getItem().getName());

        EditText editor = (EditText) findViewById(R.id.edit_description_entry);

        if (value.getDescription() != null)
            editor.setText(value.getDescription());
    }

    String date;

    @Override
    public void onBackPressed() {
        click_ok(null);
    }

    public void click_ok(View v)
    {

        if (this.getLastWindowState().equals(WindowState.OUT_OF_RANGE) && !outOfRangeDateOK)
        {
            final DoableValue value2 = value;
            SeriousConfirmationDialog dlg = new SeriousConfirmationDialog(this,
                    value.getItem().getName(), "Change value on: " + this.date,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            if (id == DialogInterface.BUTTON_POSITIVE) {
                                Save();
                            }
                        }
                    });

            dlg.show();

        }
        else
        {
            Save();
        }

    }

    private void Save() {
        EditText editor = (EditText) findViewById(R.id.edit_description_entry);

        value.setDescription(editor.getText().toString());
        tableAdapter.save(value);
        finish();
    }

    public void click_cancel(View v)
    {
        finish();
    }

}
