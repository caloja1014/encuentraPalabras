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
/**
 *
 * @author CLOJA
 */
public class InterfazIdioma  {
    private final VBox root= new VBox();
    private final Label titulo= new Label("Escoge el Idioma");

    public InterfazIdioma() {
        organizarElementos();
    }
    
    private void organizarElementos(){
        root.setSpacing(10);
        root.autosize();
        root.setAlignment(Pos.CENTER);
        final ObservableList<Node> l= root.getChildren();
        l.add(titulo);
        Idiomas[] idi=Idiomas.values();
        for (int i=0;i<idi.length;i++){
            Button btnIdioma= new Button(idi[i].toString().toUpperCase());
            l.add(btnIdioma);
        }
        
        
    }

    public VBox getRoot() {
        return root;
    }
    
}
