package com.bamon.basivoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.Languages;

import java.io.File;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    DatabaseHelper db;
    Spinner lang1, lang2;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(doesDatabaseExist(this, "vocabulary.db")){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
            finish();
        } else {
            prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            lang1 = (Spinner) findViewById(R.id.lang1Spinner);
            lang2 = (Spinner) findViewById(R.id.lang2Spinner);
            db = new DatabaseHelper(this, null, null, 1);

            db.addLanguage(new Languages(getString(R.string.german)));
            db.addLanguage(new Languages(getString(R.string.english)));
            db.addLanguage(new Languages(getString(R.string.spanish)));
            db.addLanguage(new Languages(getString(R.string.italian)));
            db.addLanguage(new Languages(getString(R.string.french)));
            loadSpinnerData();
            lang1.setSelection(0);
            lang2.setSelection(1);
        }

    }

    private void loadSpinnerData() {
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
    }

    public void nextClicked(View v){
        int l1 = lang1.getSelectedItemPosition() + 1;
        int l2 = lang2.getSelectedItemPosition() + 1;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("currentLanguage1", l1);
        editor.putInt("currentLanguage2", l2);
        editor.apply();


        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
