package arduino.control;

import javafx.scene.control.Alert;

public class Alertas {
    public void mostrarAlertError(String msm) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(msm);
        alert.showAndWait();
    }
    
    public void mostrarAlertInfo( String msm) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText(msm);
        alert.showAndWait();
    }
    
    public void mostrarAlertWarning(String msm) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Alvertencia");
        alert.setContentText(msm);
        alert.showAndWait();
    }
    
    public void mostrarAlertConfirmation(String msm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText("¿Deseas realmente confirmar?");
        alert.showAndWait();
    }
    
    public void mostrarAlertCabecera(String msm) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Cabecera");
        alert.setTitle("Info");
        alert.setContentText("Informacion sobre la aplicación");
        alert.showAndWait();
    }
}
