package com.journalfrontend;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class EntryCellFactory implements Callback<ListView<String>, ListCell<String>> {
    private HomepageController homepage;

    public EntryCellFactory(HomepageController homepage) {
        this.homepage = homepage;
    }

    @Override
    public ListCell<String> call(ListView<String> listView) {
        return new EntryListCell(homepage);
    }
}
