package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Entry implements Comparable<Entry> {
    private String timeOfEntry;
    public String entryTitle;
    private String entryContent;
    private ArrayList<String> moduleContent;

    // private UUID entryID;

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MM-dd, hh:mm");
    private static final String CONTENT_SEPARATOR = "\n---------------------";
    private static final int MODULE_MAX_SIZE = 3;

    public Entry(String entryTitle, String entryContent) {
        setTimeOfEntry();
        setEntryTitle(entryTitle);
        setEntryContent(entryContent);
        this.moduleContent = new ArrayList<>();
        // setEntryID();
    }

    public Entry(String entryTitle, String entryContent, ArrayList<String> modules) {
        setTimeOfEntry();
        setEntryTitle(entryTitle);
        setEntryContent(entryContent);
        setModuleContent(modules);
        // setEntryID();
    }

    public Entry(String timeOfEntry, String entryTitle, String entryContent) {
        setTimeOfEntry(timeOfEntry);
        setEntryTitle(entryTitle);
        setEntryContent(entryContent);
        this.moduleContent = new ArrayList<>();
        // setEntryID(entryID);
    }

    public Entry(String timeOfEntry, String entryTitle, String entryContent, ArrayList<String> modules) {
        setTimeOfEntry(timeOfEntry);
        setEntryTitle(entryTitle);
        setEntryContent(entryContent);
        setModuleContent(modules);
        // setEntryID(entryID);
    }

    public String getTimeOfEntry() {
        return timeOfEntry;
    }

    public void setTimeOfEntry() {
        LocalDateTime currentTime = LocalDateTime.now();
        String formatDate = format.format(currentTime);
        this.timeOfEntry = formatDate;
    }

    public void setTimeOfEntry(String timeOfEntry) {
        this.timeOfEntry = timeOfEntry;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryContent() {
        return entryContent;
    }

    public void setEntryContent(String entryContent) {
        this.entryContent = entryContent;
    }

    public ArrayList<String> getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(ArrayList<String> content) {
        if (!content.isEmpty() && content.size() <= MODULE_MAX_SIZE && content != null)
            this.moduleContent = content;
        else
            this.moduleContent = new ArrayList<>();
    }

    public boolean equals(Entry aE) {
        return aE != null &&
                this.entryTitle.equalsIgnoreCase(aE.getEntryTitle()) &&
                this.entryContent.equalsIgnoreCase(aE.getEntryContent());
    }

    public String toString() {
        return "[" + this.timeOfEntry + "]" + "\n"
                + "[" + this.entryTitle + "]\n\n"
                + this.entryContent +
                (moduleContent.isEmpty() ? CONTENT_SEPARATOR : "\n" + printModules() + CONTENT_SEPARATOR);
    }

    private String printModules() {
        String content = "";
        int index = 0;

        for (String module : moduleContent) {
            if (index == 0) {
                content = module;
                index = 1;
            } else
                content = content + "\n" + module;
        }

        return content;
    }

    public int compareTo(Entry aE) {
        return this.entryTitle.compareTo(aE.getEntryTitle());
    }
}