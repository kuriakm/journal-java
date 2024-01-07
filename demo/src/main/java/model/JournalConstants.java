package model;

public abstract class JournalConstants {
    // Password management
    protected static final char[] DEFAULT_PW = "9rsl02+9#!".toCharArray();
    protected static final String PW_DIALOGUE = "Enter a password for the journal (use numbers and a minimum length of 5 characters): ";
    protected static final String REENTER_PW = "Re-enter your password: ";

    // File paths for JSON files
    protected static final String DEFAULT_JOURNAL_NAME = ".journal";
    protected static final String DEFAULT_JOURNAL_PATH = "./journals/";
    protected static final String FILETYPE = ".json";

    // Menu options for JournalManager
    protected static final String CREATE_ENTRY = "(1) Create a new journal entry.";
    protected static final String REMOVE_ENTRY = "(2) Remove a journal entry.";
    protected static final String CLEAR_JOURNAL = "(3) Clear all journal entries in the journal.";
    protected static final String PRINT_JOURNAL = "(4) Print an entry or all entries in the journal.";
    protected static final String FIND_ENTRY = "(5) Find a journal entry by title.";
    protected static final String LOAD_JOURNAL = "(6) Load journal entries from a file.";
    protected static final String SAVE_JOURNAL = "(7) Save journal entries to a file.";
    protected static final String CREATE_PW = "(8) Create a password for your journal.";
    protected static final String CHANGE_PW = "(8) Change your journal password.";
    protected static final String QUIT = "Type quit or q to close the program.";

    // Module choice and options
    protected static final String ADD_MODULE = "Would you like to add any journal modules to an entry? Type yes or no.";
    protected static final String MODULE_OPTIONS = "Here is a list of the modules you can add to your entry";
    protected static final String GRATITUDE_MODULE = "(1) Gratitude Module: List some things that you are grateful for.";
    protected static final String FUTURE_LOG = "(2) Future Log Module: What are some things you are looking forward to? Write them here in the Future Log module!";
    protected static final String FEELINGS_LOG = "(3) Feelings Log Module: What are you feeling today? Write them in the Feelings Log module!";
    protected static final String MODULE_QUIT = "(9) Close the modules menu.";
}
