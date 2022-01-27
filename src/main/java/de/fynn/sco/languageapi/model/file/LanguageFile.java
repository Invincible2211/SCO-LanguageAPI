package de.fynn.sco.languageapi.model.file;

import de.fynn.sco.languageapi.model.exception.InvalidLanguageFileException;

import java.io.File;
import java.io.InputStream;

public class LanguageFile {

    public LanguageFile(File file) throws InvalidLanguageFileException {

    }

    public LanguageFile(InputStream inputStream){

    }

    public String getIdentifier(){

    }

    public String getTranslation(String key){

    }

}
