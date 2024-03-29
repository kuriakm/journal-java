package model;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
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
                        String entryContent = (String) entryJSON.get(ENTRY_CONTENT);

                        String decryptedEntryContent = jd.decrypt(jd.getPassword().toString(), entryContent);

                        // Get modules from entry if module-content is not empty/null
                        JSONArray modulesJSON = (JSONArray) entryJSON.get(MODULE_CONTENT);
                        ArrayList<String> modulesFromFile = new ArrayList<>();

                        Iterator iM = modulesJSON.iterator();

                        while (iM.hasNext()) {
                            JSONObject modJSON = (JSONObject) iM.next();
                            String module = (String) modJSON.get(MODULE);

                            String decodedModule = jd.decrypt(jd.getPassword().toString(), module);

                            modulesFromFile.add(decodedModule);
                        }

                        Entry aE = null;

                        if (modulesFromFile.isEmpty() || modulesFromFile == null)
                            aE = new Entry(timeOfEntry, decryptedEntryContent);
                        else
                            aE = new Entry(timeOfEntry, decryptedEntryContent, modulesFromFile);

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
