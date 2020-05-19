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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.Idiomas;
import modelo.LecturaIdioma;
import providers.Conexion;
import providers.Sesion;

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

    @Override
    public void start(Stage primaryStage) {
        InterfazIdioma i = new InterfazIdioma();

        Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);

        stage.setTitle("Hello World!");
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
