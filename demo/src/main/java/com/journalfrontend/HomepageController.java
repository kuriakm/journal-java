package com.journalfrontend;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Entry;

public class HomepageController {

    @FXML
    private ListView<String> entryView;

    private JournalManagerHolder jm = JournalManagerHolder.getInstance();

    @FXML
    public void initialize() {
        List<String> journalEntries = new ArrayList<>();

        // Clears ListView<String> before populating with
        // JournalManager.getJournalEntries() content
        entryView.getItems().clear();

        if (!jm.getJournalManager().isEmpty()) {
            // Sets EntryCellFactory for entryView's CellFactory
            entryView.setCellFactory(new EntryCellFactory(this));

            for (Entry entry : jm.getJournalManager().getJournalEntries())
                journalEntries.add(entry.getTimeOfEntry());

            if (!journalEntries.isEmpty())
                entryView.getItems().addAll(journalEntries);
        }
    }

    @FXML
    private void addEntry() throws IOException {
        AddEntryController addEntry = new AddEntryController();
        addEntry.setJournalManagerHolder(jm);
        addEntry.initialize();

        App.setTitle("Add Entry");
        App.setRoot("add-entry");
    }

    @FXML
    protected void openEditEntryPopup(String timeOfEntry) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("edit-entry-popup.fxml")));

        Parent root;

        try {
            root = loader.load();

            double width = 1080;
            double height = 720;

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Entry");
            popupStage.setResizable(true);

            popupStage.setScene(new Scene(root, width, height));

            EditEntryPopupController editEntry = loader.getController();

            Entry findEntry = jm.getJournalManager().findEntry(timeOfEntry);

            editEntry.setParentController(this);
            editEntry.setCurrentEntry(findEntry);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void updateEntryFromPopup(Entry entryToEdit, String entryContent) throws IOException {
        if (entryToEdit != null && !entryContent.isBlank()) {
            boolean check = jm.getJournalManager().editEntry(entryToEdit, entryContent);

            if (!check) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "There was an issue updating your entry. Please try again later.");
                App.showAlert(alert);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "There was an issue editing your entry. Please check to see if you entered valid characters for your entry.");
            App.showAlert(alert);
        }

        entryView.getItems().clear();

        for (Entry entries : jm.getJournalManager().getJournalEntries())
            entryView.getItems().add(entries.getTimeOfEntry());
    }

    @FXML
    private void closeJournal() throws IOException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        jm.getJournalManager().saveJournal();
        entryView.getItems().clear();

        jm.getJournalManager().closeJournal();
        jm.closeJournal();

        App.setRoot("primary-screen");
    }

    public void setJournalManagerHolder(JournalManagerHolder jm) {
        this.jm = jm;
    }

}
