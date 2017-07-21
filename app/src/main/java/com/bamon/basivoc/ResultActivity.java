package com.bamon.basivoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView rRI = (TextView) findViewById(R.id.rightResultIndicator);
        TextView wRI = (TextView) findViewById(R.id.wrongResultIndicator);
        TextView mS = (TextView) findViewById(R.id.motivationalSpeech);
        ImageView rIM = (ImageView) findViewById(R.id.resultIndicatorMascot);

        Intent intent = getIntent();
        Bundle daten = intent.getExtras();
        int rightVocabs = daten.getInt("Right");
        int wrongVocabs = daten.getInt("Wrong");

        rRI.setText(rightVocabs +"");
        wRI.setText(wrongVocabs +"");

        int max = rightVocabs + wrongVocabs;
        int third1 = max / 3;
        int third2= third1 * 2;

        if(rightVocabs < third1){
            mS.setText(R.string.worstResult);
            rIM.setImageResource(R.mipmap.mascot_red);
        }
        else if(rightVocabs >= third1 && rightVocabs <third2){
            mS.setText(R.string.secondWorstResult);
            rIM.setImageResource(R.mipmap.mascot_yellow);
        }
        else if(rightVocabs >= third2 && rightVocabs < max){
            mS.setText(R.string.secondBestResult);
            rIM.setImageResource(R.mipmap.mascot_green);
        }
        else{
            mS.setText(R.string.bestResult);
            rIM.setImageResource(R.mipmap.mascot_green);
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
