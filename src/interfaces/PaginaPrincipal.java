/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author CLOJA
 */
public class PaginaPrincipal {
    private VBox root;
    private Button jugar;
    
    public PaginaPrincipal(){
        root= new VBox();
        jugar=new Button("Jugar");
        root.getChildren().add(jugar);
    }
    public VBox getRoot(){
        return root;
    }
}
