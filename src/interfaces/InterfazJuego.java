/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Usuario;

/**
 *
 * @author CLOJA
 */
public class InterfazJuego {

    private HBox root;
    private AnchorPane paneJuego;
    private VBox informacion;

    private Label nivel;
    private Label puntajePartida;
    private ImageView imagenUsu;
    private Label palabra;
    private Label puntajeRecolectado;

    private final Usuario usu;
    public InterfazJuego(Usuario usu) {
        this.usu= usu;
        inicializar();
        organizarElementos();
    }

    private void organizarElementos() {
        informacion.getChildren().addAll(nivel, puntajePartida, imagenUsu, palabra, puntajeRecolectado);
        root.getChildren().addAll(paneJuego, informacion);

    }

    private void inicializar() {
        root = new HBox();
        paneJuego = new AnchorPane();
        informacion = new VBox();

        nivel = new Label("Nivel "+usu.getNivel());
        puntajePartida = new Label("100");
        imagenUsu = new ImageView();
        palabra = new Label();
        puntajeRecolectado = new Label("0");

    }

    private void agregarAcciones() {

    }

    public HBox getRoot() {
        return root;
    }
    
}
