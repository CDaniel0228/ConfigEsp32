package arduino.control;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import java.io.InputStreamReader;
import java.time.LocalDate;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import javafx.scene.control.Alert.AlertType;





public class VentanaController implements Initializable {
    Ficheros ficheros=new Ficheros();
    LocalDate fechaActual = LocalDate.now();

    String fecha="";
    Preferences preferencias=Preferences.userRoot().node("setting");;
    boolean band= true;

    @FXML
    private TextField boxWifi;
    @FXML
    private PasswordField boxPassw;
    @FXML
    private TextField boxCorreo;
    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<String> wifiComboBox;
    @FXML
    private RadioButton btnRadio;
    @FXML
    private Button btnQr;
    @FXML
    private TextArea boxArea;
    @FXML
    private Button btnAbrir;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //preferencias.remove("id");
        boxWifi.setPromptText("nombre de la red");
        ObservableList<String> wifiNetworks = getWiFiNetworks();
        wifiComboBox.setItems(wifiNetworks);
        String dato=preferencias.get("id", "");
        if(dato==""){
            System.out.println("false");
        }else{
            boxCorreo.setText(dato);
            band=false;
        }
        
        
    }
    
    
     @FXML
    private void onClick(ActionEvent event) { 
        if(event.getSource()==btnGuardar){
            if(boxEmpy()){
                Alertas alertas=new Alertas();
                alertas.mostrarAlertInfo("Se encuentran campos bacios");
                
            }else{
                ficheros.btnGuardar(boxCorreo.getText()+"\n"+wifiComboBox.getValue()+"\n"+boxPassw.getText()+"\n");
                boxArea.setText(boxCorreo.getText()+"\n"+wifiComboBox.getValue()+"\n"+boxPassw.getText()+"\n");
                preferencias.put("id", boxCorreo.getText());
                band=false;
                //preferencias.remove("");
            }
        }else if(event.getSource()==btnRadio){
            if(btnRadio.isSelected()){
                boxWifi.setVisible(true);
                wifiComboBox.setVisible(false);
            }else{
                boxWifi.setVisible(false);
                wifiComboBox.setVisible(true);
            }
        }else if(event.getSource()==btnQr){
            ByteArrayOutputStream out = QRCode.from(boxCorreo.getText()).to(ImageType.PNG).stream();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            Image qrImage = new Image(in);

            // Crear el ImageView y mostrar el código QR
            ImageView imageView = new ImageView(qrImage);

            // Configurar el tamaño del ImageView
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

            // Crear un DialogPane personalizado
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(imageView);
            
            // Crear la alerta y configurar el contenido
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Código QR");
            alert.setHeaderText(null);
            alert.setDialogPane(dialogPane);
            
            // Mostrar la alerta
            dialogPane.getButtonTypes().add(ButtonType.CLOSE);
            dialogPane.lookupButton(ButtonType.CLOSE).setVisible(false);
            alert.showAndWait();

        }else if(event.getSource()==btnAbrir){
            ficheros.btnAbrir(boxArea);

        }else if(event.getSource()==btnAgregar){
            if(boxEmpy()){
                Alertas alertas=new Alertas();
                alertas.mostrarAlertInfo("Se encuentran campos bacios");
            }else{
                if(boxArea.getParagraphs().size()<6){
                    String aux=boxArea.getText()+wifiComboBox.getValue()+"\n"+boxPassw.getText()+"\n";
                    ficheros.escribir(aux);
                    boxArea.setText(aux);
                }else{
                    Alertas alertas=new Alertas();
                    alertas.mostrarAlertInfo("No se permite agregar mas de 2 redes WIFI");
                }
                
            }

        }
        else if(event.getSource()==btnEliminar){
            Alertas alertas=new Alertas();
            if(alertas.mostrarAlertConfirmation("¿Deseas realmente eliminar el contenido del archivo?")){
                boxArea.setText(boxCorreo.getText()+"\n");
                ficheros.escribir(boxCorreo.getText()+"\n");
            }

        }
        
        
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        if(band & boxPassw.getText().length()<6){
        fecha=generarId(fechaActual.toString().replace("-", "").replace("0", ""), boxPassw.getText());
        boxCorreo.setText(fecha);
        }

    }

    public boolean boxEmpy(){
        return (wifiComboBox.getValue()==null &
        boxWifi.getText().isEmpty()) || boxPassw.getText().isEmpty();
    }
    private ObservableList<String> getWiFiNetworks() {
        ObservableList<String> wifiNetworks = FXCollections.observableArrayList();

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            wifiNetworks.addAll(getWindowsWiFiNetworks());
        } else if (os.contains("mac")) {
            wifiNetworks.addAll(getMacWiFiNetworks());
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            wifiNetworks.addAll(getLinuxWiFiNetworks());
        }
        

        return wifiNetworks;
    }

    private List<String> getLinuxWiFiNetworks() {
        List<String> wifiNetworks = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec("nmcli -t -f SSID dev wifi");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                wifiNetworks.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wifiNetworks;
    }

    private List<String> getWindowsWiFiNetworks() {
        List<String> wifiNetworks = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show networks mode=Bssid");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("SSID")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        wifiNetworks.add(parts[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wifiNetworks;
    }

    private List<String> getMacWiFiNetworks() {
        List<String> wifiNetworks = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -s");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("SSID")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length > 0) {
                        wifiNetworks.add(parts[0].trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wifiNetworks;
    }

    public static String generarId(String fecha, String passw) {
        StringBuilder sb = new StringBuilder();
        
        int length1 = fecha.length();
        int length2 = passw.length();
        int maxLength = Math.max(length1, length2);

        for (int i = 0; i < maxLength; i++) {
            if (i < length1) {
                sb.append(fecha.charAt(i));
            }
            
            if (i < length2) {
                sb.append(passw.charAt(i));
            }
        }

        return sb.toString();
    }

    


    

    
}
