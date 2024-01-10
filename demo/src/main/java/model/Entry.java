package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Entry implements Comparable<Entry> {
    private String timeOfEntry;
    private String entryContent;
    private ArrayList<String> moduleContent;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MM-dd, HH:mm:ss");
    private static final String CONTENT_SEPARATOR = "\n---------------------";
    private static final int MODULE_MAX_SIZE = 3;

    public Entry(String timeOfEntry, String entryContent) {
        setTimeOfEntry(timeOfEntry);
        setEntryContent(entryContent);
        this.moduleContent = new ArrayList<>();
    }

    public Entry(String timeOfEntry, String entryContent, ArrayList<String> modules) {
        setTimeOfEntry(timeOfEntry);
        setEntryContent(entryContent);
        setModuleContent(modules);
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
                this.timeOfEntry.equals(aE.getTimeOfEntry()) &&
                this.entryContent.equalsIgnoreCase(aE.getEntryContent());
    }

    public String toString() {
        return "[" + this.timeOfEntry + "]" + "\n\n"
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
        return this.timeOfEntry.compareTo(aE.getTimeOfEntry());
    }
}