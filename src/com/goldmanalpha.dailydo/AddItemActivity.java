package com.goldmanalpha.dailydo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AddItemActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.additem);

        Spinner s = (Spinner) findViewById(R.id.UnitTypeSpinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.unittypelist, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(R.layout.short_spinner_dropdown_item);

        s.setAdapter(adapter);
    }


    public void okClick(View view) {


        final EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.description);
        String description = emailField.getText().toString();

        final Spinner unitTypeField = (Spinner) findViewById(R.id.UnitTypeSpinner);
        String unittype = unitTypeField.getSelectedItem().toString();

    }

    public void cancelClick(View view) {

    }
}