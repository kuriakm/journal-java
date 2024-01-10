package com.journalfrontend;

import java.io.IOException;

import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

public class EntryListCell extends ListCell<String> {

    private HomepageController homepage;

    public EntryListCell(HomepageController homepage) {
        this.homepage = homepage;

        // Prevents cells from expanding horizontally
        this.prefWidthProperty().bind(this.widthProperty().subtract(20));
        this.setMaxWidth(Control.USE_PREF_SIZE);

        // Add event handler to the cell (double-click entry)
        this.setOnMouseClicked(event -> {
            try {
                if (event.getClickCount() == 2)
                    handleMouseClicked(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleMouseClicked(MouseEvent event) throws IOException {
        // Gets timeOfEntry from ListView
        String cellText = getItem();

        // Calls openEditEntryPopup from homepage to initialize popup
        homepage.openEditEntryPopup(cellText);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        // Changes text in ListView after editing entry
        setText(item);
    }
}
