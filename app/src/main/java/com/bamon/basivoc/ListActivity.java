package com.bamon.basivoc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.VocabItem;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List <VocabItem> vocList;
    private SharedPreferences prefs;
    private Switch langSwitch;
    private Context context;
    private ListView lv;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = getApplicationContext();
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        db = new DatabaseHelper(this, null, null, 1);
        langSwitch = (Switch) findViewById(R.id.langSwitch);
        langSwitch.setChecked(prefs.getBoolean("langSwitch", false));
        lv = (ListView) findViewById(R.id.listView);

        if(langSwitch.isChecked()){
            vocList = db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2));
        } else {
            vocList = db.getEntireVocabulary();
        }
        adapter = new MyAdapter(this, R.layout.vocab_item, vocList);
        lv.setAdapter(adapter);

        langSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("langSwitch", true);
                    editor.apply();
                    vocList = db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2));
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("langSwitch", false);
                    editor.apply();
                    vocList = db.getEntireVocabulary();
                }
                adapter = new MyAdapter(context, R.layout.vocab_item, vocList);
                lv.setAdapter(adapter);
            }
        });



        // long click listener for a list item, showing a delete dialog
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle(getString(R.string.delete))
                        .setMessage(getString(R.string.deleteMessage))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteVocab(vocList.get(position).get_id());
                                if(langSwitch.isChecked()){
                                    vocList = db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2));
                                } else {
                                    vocList = db.getEntireVocabulary();
                                }
                                adapter = new MyAdapter(context, R.layout.vocab_item, vocList);
                                lv.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

                return false;
            }
        });


    }


    private static class ViewHolder {
        TextView phrase1, phrase2, language1, language2;
    }

    // inner class for adapter to show the contents of the list
    private class MyAdapter extends ArrayAdapter {

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            VocabItem currentVocab = vocList.get(position);

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.vocab_item, null, false);
            }

            ViewHolder vh = new ViewHolder();
            vh.phrase1 = (TextView) convertView.findViewById(R.id.phrase1);
            vh.phrase2 = (TextView) convertView.findViewById(R.id.phrase2);
            vh.language1 = (TextView) convertView.findViewById(R.id.lang1);
            vh.language2 = (TextView) convertView.findViewById(R.id.lang2);

            convertView.setTag(vh);

            TextView phrase1 = ((ViewHolder)convertView.getTag()).phrase1;
            TextView phrase2 = ((ViewHolder)convertView.getTag()).phrase2;
            TextView lang1 = ((ViewHolder)convertView.getTag()).language1;
            TextView lang2 = ((ViewHolder)convertView.getTag()).language2;

            phrase1.setText(currentVocab.getPhrase1());
            phrase2.setText(currentVocab.getPhrase2());
            lang1.setText(db.getLanguage(currentVocab.getLanguageOfPhrase1()).getLanguage());
            lang2.setText(db.getLanguage(currentVocab.getLanguageOfPhrase2()).getLanguage());

            return convertView;
        }

    }


}
