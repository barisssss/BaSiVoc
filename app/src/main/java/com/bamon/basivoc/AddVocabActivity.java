package com.bamon.basivoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddVocabActivity extends AppCompatActivity {

    TextView l1, l2;
    Button add, accept;
    EditText l1I, l2I;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocab);

        l1 = (TextView) findViewById(R.id.language1);
        l2 = (TextView) findViewById(R.id.language2);
        add = (Button) findViewById(R.id.addButton);
        accept = (Button) findViewById(R.id.acceptButton);
        l1I = (EditText) findViewById(R.id.language1Input);
        l2I = (EditText) findViewById(R.id.language2input);
    }

    public void addButtonPressed(View v){

    }

    public void acceptButtonPressed(View v){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
