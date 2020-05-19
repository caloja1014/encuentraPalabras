/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author CLOJA
 */
public class InterfazSesion {
    private VBox root;
    private Button iniciarSesionBtn;
    private Button crearCuentaBtn;
    
    public InterfazSesion(){
        organizarElementos();
    
    }
    
    private void organizarElementos(){
        root= new VBox();
        root.setSpacing(10);
        root.autosize();
        root.setAlignment(Pos.CENTER);
        iniciarSesionBtn= new Button("Iniciar Sesion");
        
        crearCuentaBtn= new Button("Crear Cuenta");
        root.getChildren().addAll(iniciarSesionBtn,crearCuentaBtn);
        
    }

    public VBox getRoot() {
        return root;
    }
    
}
