package com.bamon.basivoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.Languages;

import java.io.File;
import java.util.List;

public class OptionsActivity extends AppCompatActivity {

    Spinner lang1, lang2;
    EditText lengthET, addLangET;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        lang1 = (Spinner) findViewById(R.id.spinner_language1);
        lang2 = (Spinner) findViewById(R.id.spinner_language2);
        lengthET = (EditText) findViewById(R.id.practiceLengthInput);

        loadSpinnerData();

        lengthET.setText(prefs.getInt("currentPracticeLength", 10) +"");

    }

    public void addLangClicked(View v){
        addLangET = (EditText) findViewById(R.id.newLangET);
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);
        db.addLanguage(new Languages(addLangET.getText()+""));
        addLangET.setText("");
        loadSpinnerData();
        Toast.makeText(this, "Language added!", Toast.LENGTH_SHORT).show();
    }

    public void acceptOptions(View v){
        int l1 = lang1.getSelectedItemPosition() + 1;
        int l2 = lang2.getSelectedItemPosition() + 1;
        int length = Integer.parseInt(lengthET.getText() + "");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("currentLanguage1", l1);
        editor.putInt("currentLanguage2", l2);
        editor.putInt("currentPracticeLength", length);
        editor.apply();

        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    private void loadSpinnerData() {
        // database handler
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);

        // Spinner Drop down elements
        List<Languages> languages = db.getAllLanguages();

        // Creating adapter for spinner
        ArrayAdapter<Languages> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, languages);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lang1.setAdapter(dataAdapter);
        lang2.setAdapter(dataAdapter);
        lang1.setSelection(prefs.getInt("currentLanguage1", 1) - 1);
        lang2.setSelection(prefs.getInt("currentLanguage2", 2) - 1);
    }

    public void clearDBPressed(View v){
        File dbFile = this.getDatabasePath("vocabulary.db");
        dbFile.delete();
        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
        finish();
    }
}
