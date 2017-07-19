package com.bamon.basivoc.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bamon.basivoc.SplashActivity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "vocabulary.db";
    private static final int DB_VERSION = 2;

    //Strings for Table names
    private static final String TABLE_VOCAB_LIST = "vocab_item";
    private static final String TABLE_LANGUAGES = "languages";

    //Reusable Columns
    private static final String COLUMN_ID = "_id";

    //Columns for VocabItem
    private static final String COLUMN_PHRASE1 = "phrase1";
    private static final String COLUMN_PHRASE2 = "phrase2";
    private static final String COLUMN_LANGUAGE_OF_PHRASE1 = "language_of_phrase1";
    private static final String COLUMN_LANGUAGE_OF_PHRASE2 = "language_of_phrase2";

    //Columns for Languages
    private static final String COLUMN_LANGUAGE = "language";

    //create table sql statement for vocab list
    private static final String CREATE_VOCAB_LIST_TABLE =
            "CREATE TABLE " + TABLE_VOCAB_LIST + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHRASE1 + " TEXT NOT NULL, " +
                COLUMN_PHRASE2 + " TEXT NOT NULL, " +
                COLUMN_LANGUAGE_OF_PHRASE1 + " INTEGER, " +
                COLUMN_LANGUAGE_OF_PHRASE2 + " INTEGER" +
            ");";

    //create table sql statement for languages
    private static final String CREATE_LANGUAGES_TABLE =
            "CREATE TABLE " + TABLE_LANGUAGES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LANGUAGE + " TEXT NOT NULL UNIQUE" +
            ");";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VOCAB_LIST_TABLE);
        db.execSQL(CREATE_LANGUAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOCAB_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);
        onCreate(db);
    }

    // METHODS FOR VOCAB_LIST

    public void addVocab(VocabItem item, int lang1, int lang2){
        SQLiteDatabase db = getWritableDatabase();

        //get id's of currently selected languages
        int lang1_id = getLanguage(lang1).get_id();
        int lang2_id = getLanguage(lang2).get_id();

        //insert values into table
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHRASE1, item.getPhrase1());
        values.put(COLUMN_PHRASE2, item.getPhrase2());
        values.put(COLUMN_LANGUAGE_OF_PHRASE1, lang1_id);
        values.put(COLUMN_LANGUAGE_OF_PHRASE2, lang2_id);

        db.insert(TABLE_VOCAB_LIST, null, values);

        db.close();
    }

    public void deleteVocab(int index){
        SQLiteDatabase db = getWritableDatabase();
        //get id of vocab to delete
        int vocab_id = getVocabItem(index).get_id();

        db.execSQL("DELETE FROM " + TABLE_VOCAB_LIST + " WHERE " + COLUMN_ID + "=\"" + vocab_id + "\";");
        db.close();
    }

    //get all vocabs of all languages in one list
    public List<VocabItem> getEntireVocabulary(){
        SQLiteDatabase db = getReadableDatabase();

        List<VocabItem> vocabulary = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_VOCAB_LIST + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                VocabItem vocab = new VocabItem();
                vocab.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
                vocab.setPhrase1(c.getString(c.getColumnIndex(COLUMN_PHRASE1)));
                vocab.setPhrase2(c.getString(c.getColumnIndex(COLUMN_PHRASE2)));
                vocab.setLanguageOfPhrase1(c.getInt(c.getColumnIndex(COLUMN_LANGUAGE_OF_PHRASE1)));
                vocab.setLanguageOfPhrase2(c.getInt(c.getColumnIndex(COLUMN_LANGUAGE_OF_PHRASE2)));

                vocabulary.add(vocab);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return vocabulary;
    }

    //get all vocabs of one langauge pair in a list
    public List<VocabItem> getVocabulary(int lang1, int lang2){
        SQLiteDatabase db = getReadableDatabase();

        //Get id's of currently selected languages
        int lang1_id = getLanguage(lang1).get_id();
        int lang2_id = getLanguage(lang2).get_id();

        //create vocabulary arraylist
        List<VocabItem> vocabulary = new ArrayList<>();

        //Select Query to select only vocabs in selected languages
        String selectQuery = "SELECT * FROM " + TABLE_VOCAB_LIST + " WHERE "
                + COLUMN_LANGUAGE_OF_PHRASE1 + " = " + lang1_id + " AND "
                + COLUMN_LANGUAGE_OF_PHRASE2 + " = " + lang2_id + ";";

        Cursor c = db.rawQuery(selectQuery, null);

        //fill the arraylist with found vocabs
        if (c.moveToFirst()) {
            do {
                VocabItem vocab = new VocabItem();
                vocab.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
                vocab.setPhrase1(c.getString(c.getColumnIndex(COLUMN_PHRASE1)));
                vocab.setPhrase2(c.getString(c.getColumnIndex(COLUMN_PHRASE2)));
                vocab.setLanguageOfPhrase1(lang1_id);
                vocab.setLanguageOfPhrase2(lang2_id);

                vocabulary.add(vocab);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return vocabulary;
    }

    // get a specific vocab item
    public VocabItem getVocabItem(int index){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " +  TABLE_VOCAB_LIST + " WHERE " +
                COLUMN_ID + " = " + index + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();

        VocabItem vocab = new VocabItem();
        vocab.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
        vocab.setPhrase1(c.getString(c.getColumnIndex(COLUMN_PHRASE1)));
        vocab.setPhrase2(c.getString(c.getColumnIndex(COLUMN_PHRASE2)));
        vocab.setLanguageOfPhrase1(c.getInt(c.getColumnIndex(COLUMN_LANGUAGE_OF_PHRASE1)));
        vocab.setLanguageOfPhrase2(c.getInt(c.getColumnIndex(COLUMN_LANGUAGE_OF_PHRASE2)));

        c.close();
        db.close();
        return vocab;
    }

    // get a specific vocab item of the currently selected languages
    public VocabItem getVocabItem(int index, int lang1, int lang2){
        SQLiteDatabase db = getReadableDatabase();

        int lang1_id = getLanguage(lang1).get_id();
        int lang2_id = getLanguage(lang2).get_id();

        String selectQuery = "SELECT * FROM " +  TABLE_VOCAB_LIST + " WHERE " +
                COLUMN_ID + " = " + index + " AND " +
                COLUMN_LANGUAGE_OF_PHRASE1 + " = " + lang1_id + " AND " +
                COLUMN_LANGUAGE_OF_PHRASE2 + " = " + lang2_id + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();

        VocabItem vocab = new VocabItem();
        vocab.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
        vocab.setPhrase1(c.getString(c.getColumnIndex(COLUMN_PHRASE1)));
        vocab.setPhrase2(c.getString(c.getColumnIndex(COLUMN_PHRASE2)));
        vocab.setLanguageOfPhrase1(c.getInt(c.getColumnIndex(COLUMN_LANGUAGE_OF_PHRASE1)));
        vocab.setLanguageOfPhrase2(c.getInt(c.getColumnIndex(COLUMN_LANGUAGE_OF_PHRASE2)));

        c.close();
        db.close();
        return vocab;
    }

    // METHODS FOR LANGUAGES

    public void addLanguage(Languages lang){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE, lang.getLanguage());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LANGUAGES, null, values);
        db.close();
    }

    public void deleteLanguage(int index){
        SQLiteDatabase db = getWritableDatabase();
        //get id of language to delete
        int lang_id = getLanguage(index).get_id();

        db.execSQL("DELETE FROM " + TABLE_LANGUAGES + " WHERE " + COLUMN_ID + "=\"" + lang_id + "\";");
        db.close();
    }

    // get a list of all languages in the database
    public List<Languages> getAllLanguages(){
        SQLiteDatabase db = getReadableDatabase();

        List<Languages> languages = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGES + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Languages lang = new Languages();
                lang.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
                lang.setLanguage(c.getString(c.getColumnIndex(COLUMN_LANGUAGE)));

                languages.add(lang);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return languages;
    }

    // get a specific language
    public Languages getLanguage(int index){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " +  TABLE_LANGUAGES + " WHERE " +
                COLUMN_ID + " = " + index + ";";

        Cursor c = db.rawQuery(selectQuery, null);

        c.moveToFirst();
        Languages lang = new Languages();
        lang.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
        lang.setLanguage(c.getString(c.getColumnIndex(COLUMN_LANGUAGE)));
        c.close();
        return lang;
    }
}