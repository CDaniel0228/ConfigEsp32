module arduino {
    requires javafx.controls;
    requires javafx.fxml;

    opens arduino to javafx.fxml;
    exports arduino;
}
