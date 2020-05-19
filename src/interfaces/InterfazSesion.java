/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import encuentrapalabras.EncuentraPalabras;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import modelo.Usuario;
import providers.Sesion;

/**
 *
 * @author CLOJA
 */
public final class InterfazSesion {

    private VBox root;
    private Label titulo;
    private TextField usuarioText;
    private PasswordField contraText;
    private Button loginBtn;
    private Hyperlink registrar;
    private Text texto;
    private TextFlow cajaRegistro;
    private VBox credenciales;
    private Sesion sesion;
    private Label validacion;

    public InterfazSesion() {
        inicializarVariables();
        agregarAcciones();
        organizarElementos();

    }

    private void organizarElementos() {
        root.setSpacing(10);
        root.autosize();
        root.setAlignment(Pos.CENTER);
        credenciales.getChildren().addAll(usuarioText, contraText, validacion);
        root.getChildren().addAll(titulo, credenciales, loginBtn, cajaRegistro);
    }

    private void inicializarVariables() {
        validacion = new Label();
        sesion = new Sesion();
        credenciales = new VBox();
        root = new VBox();
        titulo = new Label("Iniciar Sesion");
        usuarioText = new TextField();
        contraText = new PasswordField();
        loginBtn = new Button("Ingresar");
        texto = new Text("¿No tiene una cuenta?");
        registrar = new Hyperlink("registrate");
        cajaRegistro = new TextFlow(texto, registrar);
        cajaRegistro.setTextAlignment(TextAlignment.CENTER);
        usuarioText.setPromptText("Usuario");
        contraText.setPromptText("Contraseña");
        usuarioText.setFocusTraversable(false);
        contraText.setFocusTraversable(false);
        credenciales.setMaxWidth(EncuentraPalabras.screenSize.width * 0.20);
        credenciales.setAlignment(Pos.CENTER);
    }

    private void agregarAcciones() {
        loginBtn.setOnAction((ActionEvent value) -> ingresar());
        registrar.setOnAction((value) -> mostrarRegistro());
    }

    private void ingresar() {
        Usuario u = sesion.getUser(usuarioText.getText(), contraText.getText());
        if (usuarioText.getText().isEmpty() || contraText.getText().isEmpty()) {
            validacion.setText("Campos Vacios");
        } else if (u == null) {
            validacion.setText("Usuario o Contraseña incorrectas");
        } else {
            PaginaPrincipal i = new PaginaPrincipal();
            Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
            EncuentraPalabras.stage.setScene(scene);
        }
    }

    private void mostrarRegistro() {
        InterfazRegistro i = new InterfazRegistro();
        Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
        EncuentraPalabras.stage.setScene(scene);
    }

    public VBox getRoot() {
        return root;
    }

}
