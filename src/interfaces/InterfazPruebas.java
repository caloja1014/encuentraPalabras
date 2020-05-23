/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import static encuentrapalabras.EncuentraPalabras.screenSize;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 *
 * @author CLOJA
 */
public class InterfazPruebas extends Application {

    /*
    Revisar los listeners de la clase interfazPrueba y La interfaz juego, surge un problema al momento de cambiar el tamanio de la pantalla
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        InterfazJuego i = new InterfazJuego(new Usuario("carlos", "Loja", "Cloja", 1, 1100), 5);
        Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
        primaryStage.setScene(scene);
        primaryStage.setMaxHeight(screenSize.height * 0.7 + 40);
        primaryStage.setMaxWidth(screenSize.width * 0.55);
        primaryStage.setMinHeight(screenSize.height * 0.7 + 40);
        primaryStage.setMinWidth(screenSize.width * 0.55);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Integer[][] i = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        i[0][0]=null;
        System.out.println(Arrays.toString(i[0]));
        PriorityQueue<Integer> l = new PriorityQueue<>((Integer o1, Integer o2) -> o2 - o1);
        l.offer(5);
        l.offer(6);
        l.clear();
        System.out.println(l);
        
        launch(args);
        
    }
}
