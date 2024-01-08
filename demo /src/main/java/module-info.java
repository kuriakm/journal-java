module com.journalfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.journalfrontend to javafx.fxml;

    exports com.journalfrontend;

    opens model to javafx.fxml;

    exports model;

}
