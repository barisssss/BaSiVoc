package com.bamon.basivoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bamon.basivoc.R;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    int rightVocabs;
    int wrongVocabs;
    TextView rR;
    TextView wR;
    TextView rRI;
    TextView wRI;
    TextView mS;
    ImageView rIM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        rR = (TextView) findViewById(R.id.rightResults);
        wR = (TextView) findViewById(R.id.wrongResults);
        rRI = (TextView) findViewById(R.id.rightResultIndicator);
        wRI = (TextView) findViewById(R.id.wrongResultIndicator);
        mS = (TextView) findViewById(R.id.motivationalSpeech);
        rIM = (ImageView) findViewById(R.id.resultIndicatorMascot);

        Intent intent = getIntent();
        Bundle daten = intent.getExtras();
        rightVocabs = daten.getInt("Right");
        wrongVocabs = daten.getInt("Wrong");

        rRI.setText(rightVocabs+"");
        wRI.setText(wrongVocabs+"");

        int max = rightVocabs + wrongVocabs;
        int third1 = max / 3;
        int third2= third1 * 2;

        if(rightVocabs < third1){
            mS.setText("Sir, you suck!");
        }
        else if(rightVocabs >= third1 && rightVocabs <third2){
            mS.setText("Sir, you can do better!");
        }
        else if(rightVocabs >= third2 && rightVocabs < max){
            mS.setText("Quite good!");
        }
        else{
            mS.setText("My man!");
        }
    }

    public void startMenuAct(View v){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    public void startPracticeAct(View v){
        Intent i = new Intent(this, PracticeActivity.class);
        startActivity(i);
        finish();
    }
}
