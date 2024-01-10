package model;

import java.io.FileWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    protected static void saveEntries(ArrayList<Entry> entries, String fileName, JournalDecoder jd)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

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

    protected static JSONObject getEntryJSON(Entry entry, JournalDecoder jd)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        JSONObject entryDetails = new JSONObject();

        String encryptedEntryContent = jd.encrypt(jd.getPassword().toString(), entry.getEntryContent());

        entryDetails.put(TIME_OF_ENTRY, entry.getTimeOfEntry());
        entryDetails.put(ENTRY_CONTENT, encryptedEntryContent);

        JSONArray modulesJSON = new JSONArray();

        for (String module : entry.getModuleContent()) {
            if (module != null) {
                JSONObject modJSON = new JSONObject();
                try {
                    String encryptedModule = jd.encrypt(jd.getPassword().toString(), module);
                    modJSON.put(MODULE, encryptedModule);
                    modulesJSON.add(modJSON);
                } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                        | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
                    e.printStackTrace();
                }
            }
        }

        entryDetails.put(MODULE_CONTENT, modulesJSON);

        return entryDetails;
    }
}
