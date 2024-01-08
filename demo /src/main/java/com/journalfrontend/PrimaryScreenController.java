package com.journalfrontend;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryScreenController {

    @FXML
    private void createNewJournal() throws IOException {
        App.setRoot("create-journal");
    }

    @FXML
    private void openExistingJournal() throws IOException {
        App.setRoot("returning-journal");
    }
}