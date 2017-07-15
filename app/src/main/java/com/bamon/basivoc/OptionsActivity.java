package com.bamon.basivoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.Languages;

import java.util.List;

public class OptionsActivity extends AppCompatActivity {

    Spinner lang1, lang2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        lang1 = (Spinner) findViewById(R.id.spinner_language1);
        lang2 = (Spinner) findViewById(R.id.spinner_language2);

        loadSpinnerData();


    }

    private void loadSpinnerData() {
        // database handler
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);

        // Spinner Drop down elements
        List<Languages> languages = db.getAllLanguages();

        // Creating adapter for spinner
        ArrayAdapter<Languages> dataAdapter = new ArrayAdapter<Languages>(this,
                android.R.layout.simple_spinner_item, languages);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lang1.setAdapter(dataAdapter);
        lang2.setAdapter(dataAdapter);
        lang1.setSelection(0);
        lang2.setSelection(1);
    }

}
