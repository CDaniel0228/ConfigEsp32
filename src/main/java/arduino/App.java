package arduino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ventana"), 430, 400);
        stage.setTitle("Domotica");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("imagen/icono.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vista/"+fxml + ".fxml"));
        System.out.println(App.class.getResource(""));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}