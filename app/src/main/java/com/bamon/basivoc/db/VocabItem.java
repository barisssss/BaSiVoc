package com.bamon.basivoc.db;

public class VocabItem {

    private int _id;

    private String phrase1;
    private String phrase2;
    private int languageOfPhrase1;
    private int languageOfPhrase2;

    public VocabItem() {
    }

    public VocabItem(String phrase1, String phrase2) {
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
    }

    public String getPhrase1() {
        return phrase1;
    }

    public String getPhrase2() {
        return phrase2;
    }

    public void setPhrase1(String phrase1) {
        this.phrase1 = phrase1;
    }

    public void setPhrase2(String phrase2) {
        this.phrase2 = phrase2;
    }

    public int getLanguageOfPhrase1() {
        return languageOfPhrase1;
    }

    public void setLanguageOfPhrase1(int languageOfPhrase1) {
        this.languageOfPhrase1 = languageOfPhrase1;
    }

    public int getLanguageOfPhrase2() {
        return languageOfPhrase2;
    }

    public void setLanguageOfPhrase2(int languageOfPhrase2) {
        this.languageOfPhrase2 = languageOfPhrase2;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        String output = phrase1 + " - " + phrase2;

        return output;
    }
}