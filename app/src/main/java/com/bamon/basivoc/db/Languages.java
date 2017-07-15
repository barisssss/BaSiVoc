package com.bamon.basivoc.db;

public class Languages {
    private int _id;
    private String language;

    public Languages() {
    }

    public Languages(String language) {
        this.language = language;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return language;
    }
}
