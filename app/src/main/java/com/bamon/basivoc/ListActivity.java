package com.bamon.basivoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    DatabaseHelper db;
    List <VocabItem> vocList;
    SharedPreferences prefs;
    Switch langSwitch;
    Context context;
    ListView lv;
    MyAdapter adapter;

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
                    adapter = new MyAdapter(context, R.layout.vocab_item, vocList);
                    lv.setAdapter(adapter);
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("langSwitch", false);
                    editor.apply();
                    vocList = db.getEntireVocabulary();
                    adapter = new MyAdapter(context, R.layout.vocab_item, vocList);
                    lv.setAdapter(adapter);
                }
            }
        });



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new android.support.v7.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new android.support.v7.app.AlertDialog.Builder(context);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();*/

                return false;
            }
        });


    }


    private static class ViewHolder {
        TextView phrase1, phrase2, language1, language2;
    }

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
