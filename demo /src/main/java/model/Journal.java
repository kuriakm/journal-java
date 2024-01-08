package model;

import java.time.LocalDateTime;
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

    protected boolean addEntryFacade(String timeOfEntry, String content) {
        if (timeOfEntry.isBlank() || content.isBlank() || findEntry(timeOfEntry, content) != null
                || findEntry(timeOfEntry) != null)
            return false;
        return addEntry(new Entry(timeOfEntry, content));
    }

    protected boolean addEntryFacade(String timeOfEntry, String content, ArrayList<String> modules) {
        if (timeOfEntry.isBlank() || content.isBlank() || findEntry(timeOfEntry, content) != null
                || findEntry(timeOfEntry) != null)
            return false;
        return addEntry(new Entry(timeOfEntry, content, modules));
    }

    protected boolean addEntry(Entry aE) {
        journalEntries.add(aE);
        return true;
    }

    protected boolean editEntryFacade(Entry entryToEdit, String content) {
        if (entryToEdit == null || content.isBlank())
            return false;
        return editEntry(entryToEdit, content);
    }

    protected boolean editEntry(Entry entryToEdit, String content) {
        boolean checkContent = !content.equals(entryToEdit.getEntryContent());

        boolean checkIfEntryExists = findEntry(entryToEdit.getTimeOfEntry(), content) == null
                && findEntry(entryToEdit.getTimeOfEntry()) == null;
        boolean checkIfUnchanged = findEntry(entryToEdit.getTimeOfEntry()) == entryToEdit
                || findEntry(entryToEdit.getTimeOfEntry(), content) == entryToEdit;

        if (checkContent)
            entryToEdit.setEntryContent(content);

        if (checkIfEntryExists || checkIfUnchanged) {
            removeEntry(entryToEdit);
            journalEntries.add(entryToEdit);
            return true;
        }
        return false;
    }

    protected boolean removeEntryFacade(String timeOfEntry) {
        if (timeOfEntry.isBlank() || findEntry(timeOfEntry) == null)
            return false;
        Entry toRemove = findEntry(timeOfEntry);
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

    protected Entry findEntry(String timeOfEntry) {
        for (Entry aE : journalEntries) {
            if (aE.getTimeOfEntry().equals(timeOfEntry))
                return aE;
        }
        return null;
    }

    private Entry findEntry(String timeOfEntry, String content) {
        Entry toFind = new Entry(timeOfEntry, content);
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
