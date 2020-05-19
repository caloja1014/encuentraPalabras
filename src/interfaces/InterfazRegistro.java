/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author CLOJA
 */
public class InterfazRegistro {

    private VBox root;
    private Label nombreL;
    private Label apellidoL;
    private Label nomCuentaL;
    private Label contrasenaL;
    private Label repetirContrasenaL;

    private TextField nombreT;
    private TextField apellidoT;
    private TextField nomCuentaT;
    private PasswordField contrasenaT;
    private PasswordField repetirContrasenaT;

    private HBox cajaNombre;
    private HBox cajaApellido;
    private HBox cajaCuenta;
    private HBox cajaContrasena;
    private HBox cajaRepetirContra;

    private Button registrar;

    public InterfazRegistro() {
        inicializar();
        agregarAcciones();
        organizarElementos();
    }

    private void organizarElementos() {
        cajaNombre.getChildren().addAll(nombreL, nombreT);
        cajaApellido.getChildren().addAll(apellidoL, apellidoT);
        cajaCuenta.getChildren().addAll(nomCuentaL, nomCuentaT);
        cajaContrasena.getChildren().addAll(contrasenaL, contrasenaT);
        cajaRepetirContra.getChildren().addAll(repetirContrasenaL, repetirContrasenaT);
        
        cajaNombre.setAlignment(Pos.CENTER);
        cajaApellido.setAlignment(Pos.CENTER);
        cajaCuenta.setAlignment(Pos.CENTER);
        cajaContrasena.setAlignment(Pos.CENTER);
        cajaRepetirContra.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(cajaNombre, cajaApellido, cajaCuenta, cajaContrasena, cajaRepetirContra, registrar);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(8);
        
    }

    private void inicializar() {
        root = new VBox();
        registrar = new Button("Registrar");
        nombreL = new Label("Nombres: ");
        apellidoL = new Label("Apellidos: ");
        nomCuentaL = new Label("Nombre de Cuenta: ");
        contrasenaL = new Label("Contraseña: ");
        repetirContrasenaL = new Label("Repetir Contraseña: ");

        nombreT = new TextField();
        apellidoT = new TextField();
        nomCuentaT = new TextField();
        contrasenaT = new PasswordField();
        repetirContrasenaT = new PasswordField();

        cajaNombre = new HBox();
        cajaApellido = new HBox();
        cajaCuenta = new HBox();
        cajaContrasena = new HBox();
        cajaRepetirContra = new HBox();

    }

    private void agregarAcciones() {
        repetirContrasenaT.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue: " + oldValue);
            System.out.println("newValue: " + newValue);
            System.out.println("observable: " + observable);
            System.out.println(contrasenaT.getText());
        });
    }

    public VBox getRoot() {
        return root;
    }
    
}
