package com.journalfrontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Entry;

public class EditEntryPopupController {
    @FXML
    private TextField editTitleField;

    @FXML
    private TextArea editContentArea;

    @FXML
    private Button exitButton;

    private HomepageController homepage;
    private Entry currentEntry;

    public void setParentController(HomepageController homepage) {
        this.homepage = homepage;
    }

    @FXML
    private void handleExitButton() throws IOException {
        String entryTitle = editTitleField.getText();
        String entryContent = editContentArea.getText();

        this.homepage.updateEntryFromPopup(currentEntry, entryTitle, entryContent);

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setResizable(true);
        stage.close();
    }

    protected void setCurrentEntry(Entry entry) {
        if (entry != null)
            this.currentEntry = entry;

        // Adds entry content to be edited in editTitleField and editContentArea
        editTitleField.setText(entry.getEntryTitle());
        editContentArea.setText(entry.getEntryContent());

    }
}
