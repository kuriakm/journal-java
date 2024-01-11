package com.journalfrontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Entry;

public class RemoveEntryPopupController {

    @FXML
    private Button confirmButton;

    @FXML
    private Button exitButton;

    private HomepageController homepage;
    private Entry currentEntry;

    public void setParentController(HomepageController homepage) {
        this.homepage = homepage;
    }

    @FXML
    private void handleConfirmButton() throws IOException {
        homepage.removeEntryFromPopup(currentEntry, true);

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setResizable(true);
        stage.close();
    }

    @FXML
    private void handleExitButton() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setResizable(true);
        stage.close();
    }

    protected void setCurrentEntry(Entry entry) {
        if (entry != null)
            this.currentEntry = entry;
    }
}
