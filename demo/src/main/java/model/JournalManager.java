package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JournalManager extends JournalConstants {
    private static JournalManager manager;
    private Journal journal;
    private File journalFile;
    private JournalDecoder jd;

    private JournalManager() {
        initializeDirectory();
        setJournalFile(DEFAULT_JOURNAL_NAME);
        jd = JournalDecoder.getInstance(DEFAULT_PW);
        this.journal = Journal.getInstance();
    }

    public static JournalManager getManagerInstance() {
        if (manager == null)
            manager = new JournalManager();
        return manager;
    }

    private static void initializeDirectory() {
        File path = new File(DEFAULT_JOURNAL_PATH);
        if (!path.exists())
            path.mkdirs();
    }

    protected File getJournalFile() {
        return journalFile;
    }

    protected void setJournalFile(String fileName) {
        this.journalFile = new File(DEFAULT_JOURNAL_PATH + "." + fileName + FILETYPE);
    }

    public boolean findJournalFile(String fileName) {
        File findFile = new File(DEFAULT_JOURNAL_PATH + "." + fileName + FILETYPE);

        if (findFile.exists())
            return true;
        return false;
    }

    public void modifyJournalFile(String fileName) {
        if (!fileName.isBlank())
            setJournalFile(fileName);
        else
            setJournalFile(DEFAULT_JOURNAL_NAME);
    }

    public void modifyPassword(char[] password) {
        if (validatePassword(String.valueOf(password)))
            jd.facadeModifyPassword(password);
    }

    public boolean validatePassword(String password) {
        if (password.length() >= 5) {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Matcher containsLetter = letter.matcher(password);

            Pattern number = Pattern.compile("[0-9]");
            Matcher containsNumber = number.matcher(password);

            Pattern specialChar = Pattern.compile("[!@#$%^&*()_-]");
            Matcher containsSpecialChar = specialChar.matcher(password);

            return containsLetter.find() && containsNumber.find() && containsSpecialChar.find();
        }

        return false;
    }

    public boolean addEntry(String title, String content) {
        return journal.addEntryFacade(title, content);
    }

    public boolean addEntry(String title, String content, ArrayList<String> modules) {
        return journal.addEntryFacade(title, content, modules);
    }

    public boolean editEntry(Entry entryToEdit, String title, String content) {
        return journal.editEntryFacade(entryToEdit, title, content);
    }

    public boolean removeEntry(String title) {
        return journal.removeEntryFacade(title);
    }

    public boolean clearJournalConsole(String trigger) {
        if (trigger.equalsIgnoreCase("Yes") || trigger.equalsIgnoreCase("y"))
            return journal.clearJournalFacade(true);
        return false;
    }

    public Entry findEntry(String title) {
        if (!title.isBlank())
            return journal.findEntry(title);
        return null;
    }

    public ArrayList<Entry> getJournalEntries() {
        return journal.getJournalEntries();
    }

    public boolean isEmpty() {
        if (journal.isEmpty())
            return true;
        return false;
    }

    public void loadJournal() {
        if (journalFile.exists())
            journal.loadJournal(journalFile.getPath(), jd);
    }

    public void saveJournal() throws IOException {
        if (!journalFile.exists())
            journalFile.createNewFile();
        journal.saveJournal(journalFile.getPath(), jd);

    }

    public void closeJournal() {
        jd.closeJournal();
        journal.clearJournal();
    }

}