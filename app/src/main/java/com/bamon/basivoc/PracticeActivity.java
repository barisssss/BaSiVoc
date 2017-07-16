package com.bamon.basivoc;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.VocabItem;

import java.util.Random;


public class PracticeActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button tA;
    Button knew;
    Button wrong;
    TextView va;
    SharedPreferences prefs;
    int rightVocs;
    int wrongVocs;
    int length;
    ProgressBar pb;
    Random random;
    VocabItem vocab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        rightVocs = 0;
        wrongVocs = 0;
        random = new Random();
        va = (TextView) findViewById(R.id.vocAnzeige);
        tA = (Button) findViewById(R.id.turnAroundButton);
        knew = (Button) findViewById(R.id.knewButton);
        wrong = (Button) findViewById(R.id.wrongButton);
        db = new DatabaseHelper(this, null, null, 1);
        length = db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2)).size();
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(prefs.getInt("currentPracticeLength", 10));
        vocab = db.getVocabItem(random.nextInt(length),
                prefs.getInt("currentLanguage1", 1),
                prefs.getInt("currentLanguage2", 2));
        va.setText(vocab.getPhrase1());

    }

    public void turnAround(View v){
        tA.setVisibility(View.GONE);
        knew.setVisibility(View.VISIBLE);
        wrong.setVisibility(View.VISIBLE);
        va.setText(vocab.getPhrase2());
    }

    public void knewwrongClicked(View v){
        Button pressedButton = (Button) v;
        pb.setProgress(pb.getProgress()+1);
        tA.setVisibility(View.VISIBLE);
        knew.setVisibility(View.GONE);
        wrong.setVisibility(View.GONE);
        vocab = db.getVocabItem(random.nextInt(length),
                prefs.getInt("currentLanguage1", 1),
                prefs.getInt("currentLanguage2", 2));
        va.setText(vocab.getPhrase1());
        if(pressedButton.getText().equals("I knew it!")){
            rightVocs++;
        }
        else{
            wrongVocs++;
        }
        if(pb.getProgress() == pb.getMax()){
            Intent i = new Intent(this, ResultActivity.class);
            i.putExtra("Right",rightVocs);
            i.putExtra("Wrong",wrongVocs);
            startActivity(i);
        }
    }


}
