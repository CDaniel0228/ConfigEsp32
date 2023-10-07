package arduino.control;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {
    boolean band=false;

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
    
    public boolean mostrarAlertConfirmation(String msm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText(msm);
         // Customize the buttons
         ButtonType buttonTypeYes = new ButtonType("Si"); 
         alert.getButtonTypes().set(0,buttonTypeYes);
         alert.showAndWait().ifPresent(result -> {
             if (result == buttonTypeYes) {
                 band=true;
                 // Perform the action here
             } 
         });
         return band;
    }
    
}
