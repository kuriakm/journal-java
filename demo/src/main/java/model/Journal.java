package model;

import java.util.ArrayList;

public class Journal extends JournalConstants {
    private static Journal journal;
    private static ArrayList<Entry> journalEntries;

    private Journal() {
        journalEntries = new ArrayList<>();
    }

    private Journal(String loadFile, JournalDecoder jd) {
        journalEntries = DataLoader.loadEntries(loadFile, jd);
    }

    protected static Journal getInstance() {
        if (journal == null)
            journal = new Journal();
        return journal;
    }

    protected static Journal getInstance(String loadFile, JournalDecoder jd) {
        if (journal == null && jd != null)
            journal = new Journal(loadFile, jd);
        return journal;
    }

    protected ArrayList<Entry> getJournalEntries() {
        return journalEntries;
    }

    protected boolean isEmpty() {
        if (journalEntries.isEmpty())
            return true;
        return false;
    }

    protected boolean addEntryFacade(String title, String content) {
        if (title.isBlank() || content.isBlank() || findEntry(title, content) != null || findEntry(title) != null)
            return false;
        return addEntry(new Entry(title, content));
    }

    protected boolean addEntryFacade(String title, String content, ArrayList<String> modules) {
        if (title.isBlank() || content.isBlank() || findEntry(title, content) != null || findEntry(title) != null)
            return false;
        return addEntry(new Entry(title, content, modules));
    }

    protected boolean addEntry(Entry aE) {
        journalEntries.add(aE);
        return true;
    }

    protected boolean editEntryFacade(Entry entryToEdit, String title, String content) {
        if (title.isBlank() || content.isBlank())
            return false;
        return editEntry(entryToEdit, title, content);
    }

    protected boolean editEntry(Entry entryToEdit, String title, String content) {
        boolean checkTitle = !title.equals(entryToEdit.getEntryTitle());
        boolean checkContent = !content.equals(entryToEdit.getEntryContent());

        boolean checkIfEntryExists = findEntry(title, content) == null && findEntry(title) == null;
        boolean checkIfUnchanged = findEntry(title) == entryToEdit || findEntry(title, content) == entryToEdit;

        if (checkTitle)
            entryToEdit.setEntryTitle(title);
        if (checkContent)
            entryToEdit.setEntryContent(content);

        if (checkIfEntryExists || checkIfUnchanged) {
            removeEntry(entryToEdit);
            journalEntries.add(entryToEdit);
            return true;
        }
        return false;
    }

    protected boolean removeEntryFacade(String title) {
        if (title.isBlank() || findEntry(title) == null)
            return false;
        Entry toRemove = findEntry(title);
        return removeEntry(toRemove);
    }

    protected boolean removeEntry(Entry aE) {
        journalEntries.remove(aE);
        return true;
    }

    protected boolean clearJournalFacade(boolean trigger) {
        if (trigger)
            return clearJournal();
        return false;
    }

    protected boolean clearJournal() {
        journalEntries.clear();
        return true;
    }

    protected Entry findEntry(String title) {
        for (Entry aE : journalEntries) {
            if (aE.getEntryTitle().equalsIgnoreCase(title))
                return aE;
        }
        return null;
    }

    private Entry findEntry(String title, String content) {
        Entry toFind = new Entry(title, content);
        for (Entry aE : journalEntries) {
            if (aE.equals(toFind))
                return aE;
        }
        return null;
    }

    protected void loadJournal(String loadFile, JournalDecoder jd) {
        journalEntries = DataLoader.loadEntries(loadFile, jd);
    }

    protected void saveJournal(String saveFile, JournalDecoder jd) {
        DataWriter.saveEntries(journalEntries, saveFile, jd);
    }

}
