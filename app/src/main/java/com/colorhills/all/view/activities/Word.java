package com.colorhills.all.view.activities;

public class Word {
    private String word;
    private String tr_word;
    private int pic_id;

    private Word(String word, String tr_word, int pic_id) {
        this.word = word;
        this.tr_word = tr_word;
        this.pic_id = pic_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTr_word() {
        return tr_word;
    }

    public void setTr_word(String tr_word) {
        this.tr_word = tr_word;
    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }
}
