package com.example.dictyourwords.Modals;

import java.util.ArrayList;


public class ApiResponse {

    private String word = "";
    private ArrayList<Phonetics> phonetics = null;
    private ArrayList<Meanings> meanings = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(ArrayList<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    public ArrayList<Meanings> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Meanings> meanings) {
        this.meanings = meanings;
    }
}
