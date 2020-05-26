/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import encuentrapalabras.EncuentraPalabras;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelo.Usuario;

/**
 *
 * @author CLOJA
 */
public class PaginaPrincipal {
    private VBox root;
    private Button jugar;
    Usuario usuario;
   
    public PaginaPrincipal(Usuario usuario){
        this.usuario=usuario;
        root= new VBox();
        jugar=new Button("Jugar");
        root.getChildren().add(jugar);
        root.setAlignment(Pos.CENTER);
        jugar.setOnAction((val)->{
            InterfazJuego i = new InterfazJuego(usuario);
            Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
           
            EncuentraPalabras.stage.setScene(scene);
        });
    }
    public VBox getRoot(){
        return root;
    }
}
