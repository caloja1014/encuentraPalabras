/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modelo.Idiomas;
import modelo.LecturaIdioma;
import encuentrapalabras.*;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import javafx.scene.Scene;
/**
 *
 * @author CLOJA
 */
public class InterfazIdioma {

    private final VBox root = new VBox();
    private final Label titulo = new Label("Escoge el Idioma");

    public InterfazIdioma() {
        organizarElementos();
    }

    private void organizarElementos() {
        root.setSpacing(10);
        root.autosize();
        root.setAlignment(Pos.CENTER);
        final ObservableList<Node> l = root.getChildren();
        l.add(titulo);
        Idiomas[] idi = Idiomas.values();
        for (Idiomas idi1 : idi) {
            String idioma = idi1.toString();
            Button btnIdioma;
            btnIdioma = new Button(idioma.replaceFirst(String.valueOf(idioma.charAt(0)), String.valueOf(idioma.charAt(0)).toUpperCase()));
            btnIdioma.setOnAction((e)->seleccionarIdioma(idioma));
            
            
            l.add(btnIdioma);
        }

    }
    private void seleccionarIdioma(String idioma){
        LecturaIdioma l= new LecturaIdioma(idioma);
        EncuentraPalabras.idiomaJuego.clear();
        EncuentraPalabras.idiomaJuego.addAll(l.getIdiomaSet());
        InterfazSesion i = new InterfazSesion();
        Scene scene = new Scene(i.getRoot(), screenSize.width*0.7, screenSize.height*0.7);
        EncuentraPalabras.stage.setScene(scene);
    }
    
    public VBox getRoot() {
        return root;
    }

}
