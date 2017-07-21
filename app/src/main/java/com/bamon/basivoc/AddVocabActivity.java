package com.bamon.basivoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.VocabItem;

public class AddVocabActivity extends AppCompatActivity {

    private EditText l1I, l2I;
    private SharedPreferences prefs;
    private DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocab);
        dbh = new DatabaseHelper(this, null, null, 1);
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        TextView l1 = (TextView) findViewById(R.id.language1);
        TextView l2 = (TextView) findViewById(R.id.language2);
        l1I = (EditText) findViewById(R.id.language1Input);
        l2I = (EditText) findViewById(R.id.language2input);
        l1.setText(dbh.getLanguage(prefs.getInt("currentLanguage1", 1))+"");
        l2.setText(dbh.getLanguage(prefs.getInt("currentLanguage2", 2))+"");
    }

    public void addButtonPressed(View v){
        dbh.addVocab(new VocabItem(l1I.getText()+"", l2I.getText()+""), prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2));
        l1I.setText("");
        l2I.setText("");
        Toast.makeText(this, getString(R.string.added), Toast.LENGTH_SHORT).show();
    }

    public void acceptButtonPressed(View v){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
