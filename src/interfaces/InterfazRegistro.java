/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import encuentrapalabras.EncuentraPalabras;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Usuario;
import providers.Sesion;

/**
 *
 * @author CLOJA
 */
public class InterfazRegistro {

    private HBox root;
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
    private VBox labels;
    private VBox textfields;


    private Label verificacionContra;
    private Label verificacionCuenta;
    private Label verificacionGeneral;

    private Button registrar;
    private final Sesion sesion;
    private final String idioma;
    public InterfazRegistro(Sesion sesion,String idioma) {
        this.sesion = sesion;
        this.idioma=idioma;
        inicializar();
        agregarAcciones();
        organizarElementos();
    }

    private void organizarElementos() {
        labels.getChildren().addAll(nombreL,apellidoL,nomCuentaL,contrasenaL,repetirContrasenaL);
        textfields.getChildren().addAll(nombreT,apellidoT,nomCuentaT,contrasenaT,repetirContrasenaT);
        labels.setAlignment(Pos.CENTER_LEFT);
        textfields.setAlignment(Pos.CENTER_LEFT);
        labels.setSpacing(16);
        textfields.setSpacing(15);
        root.getChildren().addAll(labels,textfields);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(8);

    }

    private void inicializar() {
        root = new HBox();
        verificacionCuenta = new Label();
        verificacionContra = new Label();
        verificacionGeneral = new Label();
        labels= new VBox();
        textfields= new VBox();
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


    }

    private void agregarAcciones() {
        nomCuentaT.textProperty().addListener((obs, oldValue, newValue) -> {
            if (sesion.verificacionCuenta(newValue)) {
                verificacionCuenta.setText("La cuenta ya existe, elija otro nombre");

            } else {
                verificacionCuenta.setText("");
            }
        });
        repetirContrasenaT.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(contrasenaT.getText())) {
                verificacionContra.setText("La contraseña no es igual");
            } else {
                verificacionContra.setText("");
            }

        });

        registrar.setOnAction((value) -> registrarUsuario());
    }

    private void registrarUsuario() {
        if (nombreT.getText().isEmpty()
                || apellidoT.getText().isEmpty()
                || nomCuentaT.getText().isEmpty()
                || contrasenaT.getText().isEmpty()
                || repetirContrasenaT.getText().isEmpty()) {
            verificacionGeneral.setText("Algunos Campos estan Vacios");
        } else {
            verificacionGeneral.setText("");
            Usuario usuario = new Usuario(nombreT.getText(), apellidoT.getText(), nomCuentaT.getText(), 1, 0);
            sesion.createUser(nombreT.getText(), apellidoT.getText(), nomCuentaT.getText(), contrasenaT.getText());
            PaginaPrincipal i = new PaginaPrincipal(usuario,idioma);
            Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
            EncuentraPalabras.stage.setScene(scene);
        }
    }

    public HBox getRoot() {
        return root;
    }

}
