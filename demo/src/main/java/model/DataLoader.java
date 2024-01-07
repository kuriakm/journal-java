package model;

import java.io.File;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    protected static final DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MM-dd, hh:mm");

    public static ArrayList<Entry> loadEntries(String fileName, JournalDecoder jd) {
        File loadFile = new File(fileName);

        ArrayList<Entry> entries = new ArrayList<>();
        if (loadFile.exists()) {
            try {
                FileReader reader = new FileReader(loadFile);
                JSONArray entriesJSON = (JSONArray) new JSONParser().parse(reader);

                for (Object objectEntry : entriesJSON) {
                    JSONObject entryJSON = (JSONObject) objectEntry;

                    if (entryJSON != null) {
                        String timeOfEntry = (String) entryJSON.get(TIME_OF_ENTRY);
                        String entryTitle = (String) entryJSON.get(ENTRY_TITLE);
                        String entryContent = (String) entryJSON.get(ENTRY_CONTENT);
                        // UUID entryID = UUID.fromString((String) entryJSON.get(ID));

                        String decodedEntryTitle = jd.decode(entryTitle);
                        String decodedEntryContent = jd.decode(entryContent);

                        // Get modules from entry if module-content is not empty/null
                        JSONArray modulesJSON = (JSONArray) entryJSON.get(MODULE_CONTENT);
                        ArrayList<String> modulesFromFile = new ArrayList<>();

                        Iterator iM = modulesJSON.iterator();

                        while (iM.hasNext()) {
                            JSONObject modJSON = (JSONObject) iM.next();
                            String module = (String) modJSON.get(MODULE);

                            String decodedModule = jd.decode(module);

                            modulesFromFile.add(decodedModule);
                        }

                        Entry aE = null;

                        if (modulesFromFile.isEmpty() || modulesFromFile == null)
                            aE = new Entry(timeOfEntry, decodedEntryTitle, decodedEntryContent);
                        else
                            aE = new Entry(timeOfEntry, decodedEntryTitle, decodedEntryContent, modulesFromFile);

                        if (aE != null && !entries.contains(aE))
                            entries.add(aE);
                    }
                }
                reader.close();
                return entries;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
