package com.example.android.ordericecream;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Altran on 10/10/2016.
 */

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
