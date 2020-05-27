/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encuentrapalabras;

import interfaces.InterfazIdioma;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.Set;
import java.util.TreeSet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import providers.Conexion;

/**
 *
 * @author CLOJA
 */
public class EncuentraPalabras extends Application {

    private final static Toolkit t = Toolkit.getDefaultToolkit();
    public final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final static Set<String> idiomaJuego = new TreeSet<>();
    public final static Stage stage = new Stage();
    private final static Conexion conexion = new Conexion();
    public final static Connection db = conexion.getConnection();
    public final static TreeSet<String> diccionarioPrueba = new TreeSet<>();
    
    @Override
    public void start(Stage primaryStage) {
        
        stage.setMaxHeight(screenSize.height * 0.7+39);
        stage.setMaxWidth(screenSize.width * 0.55);
        stage.setMinHeight(screenSize.height * 0.7+39);
        stage.setMinWidth(screenSize.width * 0.55);
        
        InterfazIdioma i = new InterfazIdioma();

        Scene scene = new Scene(i.getRoot(), screenSize.width * 0.55, screenSize.height * 0.7);
        i.getRoot().setId("idiomaPane");
        scene.getStylesheets().add("file:src/estilos/estiloBotones.css");
        stage.setTitle("Ecuentra Palabras");
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

}
