package com.journalfrontend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class AddEntryController {

    private String currentTime;

    @FXML
    private TextArea entryContentArea;

    private JournalManagerHolder jm = JournalManagerHolder.getInstance();
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MM-dd, HH:mm:ss");

    @FXML
    private void createEntry() throws IOException {
        String timeOfEntry = currentTime;
        String entryContent = entryContentArea.getText();

        if (entryContent != null) {
            boolean check = jm.getJournalManager().addEntry(timeOfEntry, entryContent);

            if (check) {
                entryContentArea.clear();

                LocalDateTime currentTime = LocalDateTime.now();
                String formatDate = format.format(currentTime);

                this.currentTime = formatDate;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Failed to create entry. Please check if you used valid characters in the entry.");
                App.showAlert(alert);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Failed to create entry. Please enter valid characters in the entry.");
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

    public void initialize() {
        LocalDateTime currentTime = LocalDateTime.now();
        String initialTime = format.format(currentTime);

        this.currentTime = initialTime;
    }

}
