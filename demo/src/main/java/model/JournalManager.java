package model;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

    public void modifyPassword(String password) {
        if (validatePassword(password))
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

    public boolean addEntry(String timeOfEntry, String content) {
        return journal.addEntryFacade(timeOfEntry, content);
    }

    public boolean addEntry(String timeOfEntry, String content, ArrayList<String> modules) {
        return journal.addEntryFacade(timeOfEntry, content, modules);
    }

    public boolean editEntry(Entry entryToEdit, String content) {
        return journal.editEntryFacade(entryToEdit, content);
    }

    public boolean removeEntry(String timeOfEntry) {
        return journal.removeEntryFacade(timeOfEntry);
    }

    public boolean clearJournalConsole(String trigger) {
        if (trigger.equalsIgnoreCase("Yes") || trigger.equalsIgnoreCase("y"))
            return journal.clearJournalFacade(true);
        return false;
    }

    public Entry findEntry(String timeOfEntry) {
        if (timeOfEntry != null)
            return journal.findEntry(timeOfEntry);
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

    public void saveJournal() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (!journalFile.exists())
            journalFile.createNewFile();
        journal.saveJournal(journalFile.getPath(), jd);

    }

    public void closeJournal() {
        jd.closeJournal();
        journal.clearJournal();
    }

}
