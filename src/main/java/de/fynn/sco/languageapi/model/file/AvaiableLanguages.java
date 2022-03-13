package de.fynn.sco.languageapi.model.file;

public enum AvaiableLanguages {

    de_DE("de_DE"), en_EN("en_EN");

    private final String value;

    private AvaiableLanguages(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
