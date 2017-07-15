package com.bamon.basivoc;

public class VocabItem {

    private int _id;
    private String lang1;
    private String lang2;


    private String word1;
    private String word2;


    public VocabItem(String lang1, String lang2, String word1, String word2) {
        this.lang1 = lang1;
        this.lang2 = lang2;
        this.word1 = word1;
        this.word2 = word2;

    }

    public String getLang1() {
        return lang1;
    }

    public void setLang1(String lang1) {
        this.lang1 = lang1;
    }

    public String getLang2() {
        return lang2;
    }

    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    public String getWord1() {
        return word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    @Override
    public String toString() {
        String output = word1 + " - " + word2;

        return output;
    }
}