
package arduino.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


public class Ficheros {
     FileChooser fileChooser=new FileChooser();
     File archivo;
     FileInputStream entrada;
     FileOutputStream salida;
     FileWriter mensaje;
     
     
    public boolean Guardar(File archivo, String document){
        boolean mensaje=false;
        try{
            salida=new FileOutputStream(archivo);
            byte[] txt=document.getBytes();
            salida.write(txt);
            mensaje=true;
        }catch(Exception e){
            
        }
        return mensaje;
    }
    
    public void btnGuardar(String area){
        fileChooser.setInitialFileName("Configuracion_Esp32.txt");
        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            archivo=archivoSeleccionado.getAbsoluteFile();
            Alertas alertas=new Alertas();
            
            if (archivoSeleccionado.getName().equals("Configuracion_Esp32.txt")) {
                boolean resultado = Guardar(archivoSeleccionado, area);
                
                if (resultado) {
                    alertas.mostrarAlertInfo("Se ha guardado correctamente.");
                } else {
                    alertas.mostrarAlertInfo("No se pudo guardar el archivo.");
                }
            } else {
                alertas.mostrarAlertError("El archivo debe tener como \n nombre Configuracion_Esp32.txt");
            }
        }
    }
    
    public String Abrir(File archivo){
        String document="";
        try{
            entrada=new FileInputStream(archivo);
            int ascci;
            while((ascci=entrada.read())!=-1){
                char caracter=(char)ascci;
                document+=caracter;
            }
        }catch(Exception e){
            
        }
        return document;
    }
    
    public void btnAbrir(TextField area){
        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            if (archivoSeleccionado.canRead()) {
                if (archivoSeleccionado.getName().endsWith(".txt")) {
                    String contenido = Abrir(archivoSeleccionado);
                    area.setText(contenido);
                } else {
                    System.out.println("Archivo no compatible");
                }
            }
        }
                
    }
 
    public void escribir(TextField area) {
        
        try{   
            
        mensaje =new FileWriter(archivo);
        String document= area.getText();
        BufferedWriter editar=new BufferedWriter(mensaje);
        editar.append(document);
        
        editar.close();
        System.out.println("El Archivo Se Edito");
            
        }catch(IOException e){
            System.out.println("Debe Elegir un Archivo");
        }
    }
    
    
}
