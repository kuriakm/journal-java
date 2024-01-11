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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
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
            // Sets up custom CellFactory for entryView's CellFactory
            entryView.setCellFactory(param -> new ListCell<String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        // Prevents cells from expanding horizontally
                        this.prefWidthProperty().bind(this.widthProperty().subtract(20));
                        this.setMaxWidth(Control.USE_PREF_SIZE);

                        // Create buttons for edit entry and remove entry functions
                        Button editEntryButton = new Button("Edit");
                        Button removeEntryButton = new Button("Remove");

                        // Connect popup controller methods to button actions
                        editEntryButton.setOnAction(event -> {
                            try {
                                handleEditEntryButton(item);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        removeEntryButton.setOnAction(event -> {
                            try {
                                handleRemoveEntryButton(item);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        // Set up HBox cell style to be shown in ListView
                        editEntryButton.setStyle("-fx-pref-width: 175px;");
                        removeEntryButton.setStyle("-fx-pref-width: 175px;");
                        HBox buttonBox = new HBox(editEntryButton, removeEntryButton);
                        buttonBox.setSpacing(30);
                        buttonBox.setPadding(new Insets(8, 8, 8, 8));

                        // Set HBox as the cell view with additonal stylization
                        setAlignment(Pos.CENTER_LEFT);
                        setText("\t" + item);
                        setGraphic(buttonBox);
                    }
                }
            });

            for (Entry entry : jm.getJournalManager().getJournalEntries())
                journalEntries.add(entry.getTimeOfEntry());

            if (!journalEntries.isEmpty())
                entryView.getItems().addAll(journalEntries);
        }
    }

    private void handleEditEntryButton(String item) throws IOException {
        openEditEntryPopup(item);
    }

    private void handleRemoveEntryButton(String item) throws IOException {
        openRemoveEntryPopup(item);
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
    protected void openRemoveEntryPopup(String timeOfEntry) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("remove-entry-popup.fxml")));

        Parent root;

        try {
            root = loader.load();

            double width = 720;
            double height = 480;

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Confirm Choice");
            popupStage.setResizable(true);

            popupStage.setScene(new Scene(root, width, height));

            RemoveEntryPopupController removeEntry = loader.getController();

            Entry findEntry = jm.getJournalManager().findEntry(timeOfEntry);

            removeEntry.setParentController(this);
            removeEntry.setCurrentEntry(findEntry);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void removeEntryFromPopup(Entry entryToEdit, boolean choice) throws IOException {
        if (entryToEdit != null && choice) {
            boolean check = jm.getJournalManager().removeEntry(entryToEdit.getTimeOfEntry());

            if (!check) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "There was an issue removing your entry. Please try again later.");
                App.showAlert(alert);
            }
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
