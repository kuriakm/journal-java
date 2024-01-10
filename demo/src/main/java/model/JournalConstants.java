package model;

public abstract class JournalConstants {
    // Password management
    protected static final String DEFAULT_PW = "9rsl02+9#!";
    protected static final String PW_DIALOGUE = "Enter a password for the journal (use numbers and a minimum length of 5 characters): ";
    protected static final String REENTER_PW = "Re-enter your password: ";

    // File paths for JSON files
    protected static final String DEFAULT_JOURNAL_NAME = ".journal";
    protected static final String DEFAULT_JOURNAL_PATH = "./journals/";
    protected static final String FILETYPE = ".json";

    // Module choice and options
    protected static final String ADD_MODULE = "Would you like to add any journal modules to an entry? Type yes or no.";
    protected static final String MODULE_OPTIONS = "Here is a list of the modules you can add to your entry";
    protected static final String GRATITUDE_MODULE = "(1) Gratitude Module: List some things that you are grateful for.";
    protected static final String FUTURE_LOG = "(2) Future Log Module: What are some things you are looking forward to? Write them here in the Future Log module!";
    protected static final String FEELINGS_LOG = "(3) Feelings Log Module: What are you feeling today? Write them in the Feelings Log module!";
    protected static final String MODULE_QUIT = "(9) Close the modules menu.";
}
