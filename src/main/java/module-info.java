module arduino {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires javase;
    requires core;



    opens arduino.control to javafx.fxml;
    exports arduino.control;
    exports arduino;
}


