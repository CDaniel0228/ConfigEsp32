
package arduino.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;


public class Ficheros {
     FileChooser fileChooser=new FileChooser();
     File archivo;
     FileInputStream entrada;
     FileOutputStream salida;
     FileWriter mensaje;
     Alertas alertas=new Alertas();
     
     
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
    
    public void btnAbrir(TextArea area){
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            archivo=archivoSeleccionado.getAbsoluteFile();
            if (archivoSeleccionado.canRead()) {
                if (archivoSeleccionado.getName().endsWith(".txt")) {
                    String contenido = Abrir(archivoSeleccionado);
                    area.setText(contenido);
                } else {
                    alertas.mostrarAlertWarning("Archivo no compatible");
                }
            }
        }
                
    }
 
    public void escribir(String area) {
        try{
            if(archivo!=null){
                mensaje =new FileWriter(archivo);
                BufferedWriter editar=new BufferedWriter(mensaje);
                editar.write(area);
                editar.close();
            }else{
                alertas.mostrarAlertError("Debe guardar o abrir un archivo");
            }          
        }catch(IOException e){
            
        }
    }
    
    
}
