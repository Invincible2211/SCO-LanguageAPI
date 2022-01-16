package de.fynn.sco.languageapi.language;

import java.util.HashMap;

public class Language {

    private final HashMap<String,String> messages;

    public Language(HashMap<String,String> messages){
        this.messages = messages;
    }

    public void addValue(String path, String value){
        messages.put(path, value);
    }

    public String getValue(String path){
        return messages.get(path);
    }

}
