package com.bamon.basivoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bamon.basivoc.db.DatabaseHelper;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void startListAct(View v){
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }

    public void startOptionsAct(View v){
        Intent i = new Intent(this, OptionsActivity.class);
        startActivity(i);
    }

    public void startPracticeAct(View v){
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        if(db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2)).size() == 0){
            Toast.makeText(this, getString(R.string.toastAddPhrases), Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, PracticeActivity.class);
            startActivity(i);
        }
    }

    public void startEditAct(View v){
        Intent i = new Intent(this, AddVocabActivity.class);
        startActivity(i);
    }
}
