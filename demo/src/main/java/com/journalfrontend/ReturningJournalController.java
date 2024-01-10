package com.journalfrontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ReturningJournalController {

    @FXML
    private TextField journalNameField;

    @FXML
    private PasswordField passwordField;

    private JournalManagerHolder jm = JournalManagerHolder.getInstance();

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary-screen");
    }

    @FXML
    private void openJournal() throws IOException {
        String journalName = journalNameField.getText();
        String password = passwordField.getText();

        if (journalName != null && password != null) {
            if (!jm.getJournalManager().findJournalFile(journalName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Failed to find journal. Please enter the name of an existing journal.");
                App.showAlert(alert);
            } else {
                jm.getJournalManager().modifyJournalFile(journalName);
                jm.getJournalManager().modifyPassword(password);

                jm.getJournalManager().loadJournal();

                HomepageController homepage = new HomepageController();
                homepage.setJournalManagerHolder(jm);

                App.setRoot("homepage");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Failed to open journal. Please enter a valid name and password.");
            App.showAlert(alert);
        }
    }
}
