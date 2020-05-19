/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import encuentrapalabras.EncuentraPalabras;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 *
 * @author CLOJA
 */
public class InterfazSesion {
    private VBox root;
    private Label titulo;
    private TextField usuarioText;
    private PasswordField contraText;
    private Button loginBtn;
    private Hyperlink registrar;
    private Text texto;
    private TextFlow cajaRegistro;
    private VBox credenciales;
    public InterfazSesion(){
        inicializarVariables();
        organizarElementos();
    
    }
    
    private void organizarElementos(){ 
        root.setSpacing(10);
        root.autosize();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titulo,credenciales,loginBtn,cajaRegistro);
    }
    private void inicializarVariables(){
        credenciales= new VBox();
        root= new VBox();
        titulo= new Label("Iniciar Sesion");
        usuarioText=new TextField();
        contraText=  new PasswordField();
        loginBtn= new Button("Ingresar");
        texto=new Text("¿No tiene una cuenta?");
        registrar=new Hyperlink("registrate");
        cajaRegistro= new TextFlow(texto,registrar);
        cajaRegistro.setTextAlignment(TextAlignment.CENTER);
        usuarioText.setPromptText("Usuario");
        contraText.setPromptText("Contraseña");
        
        credenciales.getChildren().addAll(usuarioText,contraText);
        credenciales.setMaxWidth(EncuentraPalabras.screenSize.width*0.20);
    }
    public VBox getRoot() {
        return root;
    }
    
}
