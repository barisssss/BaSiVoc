package com.bamon.basivoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
