package com.bamon.basivoc;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bamon.basivoc.db.DatabaseHelper;


public class PracticeActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button tA;
    Button knew;
    Button wrong;
    TextView va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        va = (TextView) findViewById(R.id.vocAnzeige);
        tA = (Button) findViewById(R.id.turnAroundButton);
        knew = (Button) findViewById(R.id.knewButton);
        wrong = (Button) findViewById(R.id.wrongButton);
        db = new DatabaseHelper(this, null, null, 1);
        va.setText(db.getVocabItem(1, 1, 2).getPhrase1());

    }

    public void turnAround(View v){
        tA.setVisibility(View.GONE);
        knew.setVisibility(View.VISIBLE);
        wrong.setVisibility(View.VISIBLE);
        va.setText(db.getVocabItem(1, 1, 2).getPhrase2());
    }

    public void knewwrongClicked(View v){
        tA.setVisibility(View.VISIBLE);
        knew.setVisibility(View.GONE);
        wrong.setVisibility(View.GONE);
        va.setText(db.getVocabItem(2, 1, 2).getPhrase1());
    }


}
