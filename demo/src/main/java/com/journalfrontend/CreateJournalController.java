package com.journalfrontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateJournalController {

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
    private void initJournal() throws IOException {
        String journalName = journalNameField.getText();
        String password = passwordField.getText();

        if (journalName != null && password != null) {
            if (!jm.getJournalManager().findJournalFile(journalName)) {
                if (jm.getJournalManager().validatePassword(password)) {

                    jm.getJournalManager().modifyJournalFile(journalName);
                    jm.getJournalManager().modifyPassword(password.toCharArray());

                    HomepageController homepage = new HomepageController();
                    homepage.setJournalManagerHolder(jm);

                    App.setRoot("homepage");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "The password you entered is invalid. Please use a minimum of 5 characters, a number, and special characters ([!@#$%^&*()_-]).");
                    App.showAlert(alert);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "This journal already exists. Please enter a different name for your journal.");
                App.showAlert(alert);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Failed to create journal. Please enter a valid name and password.");
            App.showAlert(alert);
        }
    }
}
