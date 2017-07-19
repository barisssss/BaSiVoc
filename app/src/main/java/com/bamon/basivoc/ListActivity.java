package com.bamon.basivoc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bamon.basivoc.db.DatabaseHelper;
import com.bamon.basivoc.db.Languages;
import com.bamon.basivoc.db.VocabItem;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DatabaseHelper db;
    List <VocabItem> vocList;
    SharedPreferences prefs;
    Switch langSwitch;
    Context context;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = getApplicationContext();
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        db = new DatabaseHelper(this, null, null, 1);
        langSwitch = (Switch) findViewById(R.id.langSwitch);
        lv = (ListView) findViewById(R.id.listView);
        if(langSwitch.isChecked()){
            vocList = db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2));
            lv.setAdapter(new MyAdapter(context, R.layout.vocab_item, vocList));
        } else {
            vocList = db.getEntireVocabulary();
            lv.setAdapter(new MyAdapter(context, R.layout.vocab_item, vocList));
        }

        langSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    vocList = db.getVocabulary(prefs.getInt("currentLanguage1", 1), prefs.getInt("currentLanguage2", 2));
                    lv.setAdapter(new MyAdapter(context, R.layout.vocab_item, vocList));
                } else {
                    vocList = db.getEntireVocabulary();
                    lv.setAdapter(new MyAdapter(context, R.layout.vocab_item, vocList));
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
                DatabaseHelper db = new DatabaseHelper(context, null, null, 1);
                return false;
            }
        });


    }


    private static class ViewHolder {
        TextView language1, language2;
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
            vh.language1 = (TextView) convertView.findViewById(R.id.lang1);
            vh.language2 = (TextView) convertView.findViewById(R.id.lang2);

            convertView.setTag(vh);

            TextView lang1 = ((ViewHolder)convertView.getTag()).language1;
            TextView lang2 = ((ViewHolder)convertView.getTag()).language2;

            lang1.setText(currentVocab.getPhrase1());
            lang2.setText(currentVocab.getPhrase2());

            return convertView;
        }

    }


}
