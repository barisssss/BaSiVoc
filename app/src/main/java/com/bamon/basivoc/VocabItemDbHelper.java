package com.bamon.basivoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VocabItemDbHelper extends SQLiteOpenHelper{


    public static final String DB_NAME = "vocabulary.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_VOCAB_LIST = "vocab_item";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LANG1 = "lang1";
    public static final String COLUMN_LANG2 = "lang2";
    public static final String COLUMN_WORD1 = "word1";
    public static final String COLUMN_WORD2 = "word2";


    public VocabItemDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_VOCAB_LIST +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_LANG1 + " TEXT NOT NULL, " +
                        COLUMN_LANG2 + " TEXT NOT NULL, " +
                        COLUMN_WORD1 + " TEXT NOT NULL, " +
                        COLUMN_WORD2 + " TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOCAB_LIST);
        onCreate(db);
    }

    public void addVocab(VocabItem item){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANG1, item.getLang1());
        values.put(COLUMN_LANG2, item.getLang2());
        values.put(COLUMN_WORD1, item.getWord1());
        values.put(COLUMN_WORD2, item.getWord2());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_VOCAB_LIST, null, values);
        db.close();
    }

    public void deleteVocab(VocabItem item){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VOCAB_LIST + " WHERE " + COLUMN_WORD1 + "=\"" + item.getWord1() + "\"" + " AND " + COLUMN_WORD2 + "=\"" + item.getWord2() + "\";");
    }
}