package com.example.dictyourwords.Modals;


import java.util.ArrayList;

public class Meanings {
    private String partOfSpeech = "";
    private ArrayList<Definitions> definitions = null;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public ArrayList<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definitions> definitions) {
        this.definitions = definitions;
    }
}
