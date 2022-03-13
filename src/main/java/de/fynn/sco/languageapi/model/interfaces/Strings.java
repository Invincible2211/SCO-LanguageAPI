package de.fynn.sco.languageapi.model.interfaces;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public interface Strings {

    String PARENT_FOLDER_NAME = "/Language";

    String DE_LANGUAGE_FILE = "de_de.language";

    String EN_LANGUAGE_FILE = "en_us.language";

    String COMMAND_ONLY_FOR_PLAYERS = "This command is only for players!";

    String INVALID_LANGUAGE_FILE_EXCEPTION = "The submitted file is invalid. Please check that the file is formatted correctly and that it has the .language extension.";

    String DEFAULT_LANGUAGE_FILE_DOES_NOT_EXISTS = "The submitted language in the config file is invalid. The Plugin will be disabled";

    String DATABASE_CONNECTION_FAILED = "No connection to the database could be established. Check your settings in Config.yml. The plugin will be disabled";

}
