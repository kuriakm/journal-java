package model;

import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    protected static void saveEntries(ArrayList<Entry> entries, String fileName, JournalDecoder jd) {

        JSONArray entriesJSON = new JSONArray();

        for (Entry entry : entries)
            entriesJSON.add(getEntryJSON(entry, jd));

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(entriesJSON.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static JSONObject getEntryJSON(Entry entry, JournalDecoder jd) {
        JSONObject entryDetails = new JSONObject();

        String encodedTimeOfEntry = jd.encode(entry.getTimeOfEntry());
        String encodedEntryContent = jd.encode(entry.getEntryContent());

        entryDetails.put(TIME_OF_ENTRY, encodedTimeOfEntry);
        entryDetails.put(ENTRY_CONTENT, encodedEntryContent);

        JSONArray modulesJSON = new JSONArray();

        for (String module : entry.getModuleContent()) {
            if (module != null) {
                JSONObject modJSON = new JSONObject();
                String encodedModule = jd.encode(module);
                modJSON.put(MODULE, encodedModule);
                modulesJSON.add(modJSON);
            }
        }

        entryDetails.put(MODULE_CONTENT, modulesJSON);

        return entryDetails;
    }
}
