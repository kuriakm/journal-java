package com.journalfrontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddEntryController {

    @FXML
    private TextField entryTitleField;

    @FXML
    private TextArea entryContentArea;

    private JournalManagerHolder jm = JournalManagerHolder.getInstance();

    @FXML
    private void createEntry() throws IOException {
        String entryTitle = entryTitleField.getText();
        String entryContent = entryContentArea.getText();

        if (entryTitle != null && entryContent != null) {
            boolean check = jm.getJournalManager().addEntry(entryTitle, entryContent);

            if (check) {
                entryContentArea.clear();
                entryTitleField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Failed to create entry. Please use a different entry title.");
                App.showAlert(alert);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Failed to create entry. Please enter a valid title and content.");
            App.showAlert(alert);
        }
    }

    @FXML
    private void switchToHomepage() throws IOException {
        HomepageController homepage = new HomepageController();
        homepage.setJournalManagerHolder(jm);

        App.setRoot("homepage");
    }

    public void setJournalManagerHolder(JournalManagerHolder jm) {
        this.jm = jm;
    }

}
