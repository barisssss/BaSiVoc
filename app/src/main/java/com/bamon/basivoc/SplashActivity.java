package com.bamon.basivoc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.bamon.basivoc.db.DatabaseHelper;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    DatabaseHelper db;
    Spinner lang1, lang2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(doesDatabaseExist(this, "vocabulary.db")){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        } else {

            db = new DatabaseHelper(this, null, null, 1);

        }

    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
