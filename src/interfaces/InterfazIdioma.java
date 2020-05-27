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
import javafx.scene.text.TextAlignment;
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
        root.setSpacing(15);
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
            btnIdioma.setMaxSize(500, 50);
            btnIdioma.setPrefSize(500, 50);
            btnIdioma.setMinSize(500, 50);
            btnIdioma.setTextAlignment(TextAlignment.CENTER);
            
            
            l.add(btnIdioma);
        }

    }
    private void seleccionarIdioma(String idioma){
        LecturaIdioma l= new LecturaIdioma(idioma);
        EncuentraPalabras.idiomaJuego.clear();
        EncuentraPalabras.idiomaJuego.addAll(l.getIdiomaSet());
        InterfazSesion i = new InterfazSesion(idioma);
        Scene scene = new Scene(i.getRoot(), screenSize.width*0.55, screenSize.height*0.7);
        i.getRoot().setId("registroPane");
        scene.getStylesheets().add("file:src/estilos/estiloBotones2.css");
        EncuentraPalabras.stage.setScene(scene);
    }
    
    public VBox getRoot() {
        return root;
    }

}
