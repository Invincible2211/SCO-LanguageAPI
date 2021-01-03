package de.fynn.mystic.mysticlanguageapi.language;

import java.util.HashMap;

public class Language {

    private final HashMap<Integer,String> messages;

    public Language(HashMap<Integer,String> messages){
        this.messages = messages;
    }

    public String getValue(int key){
        return messages.get(key);
    }

}
