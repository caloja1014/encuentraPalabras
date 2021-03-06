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
import javafx.scene.layout.VBox;
import modelo.Usuario;

/**
 *
 * @author CLOJA
 */
public class PaginaPrincipal {

    private VBox root;
    private Button jugar;
    private Usuario usuario;
    private String idioma;

    public PaginaPrincipal(Usuario usuario, String idioma) {
        this.usuario = usuario;
        this.idioma = idioma;
        root = new VBox();
        jugar = new Button("Jugar");
        root.getChildren().add(jugar);
        root.setAlignment(Pos.CENTER);
        root.setId("inicioPane");
        jugar.setOnAction((val) -> {
            InterfazJuego i = new InterfazJuego(usuario, idioma);
            Scene scene = new Scene(i.getRoot(), screenSize.width * 0.55, screenSize.height * 0.7);
            i.getRoot().setId("paneJuego");
            scene.getStylesheets().add("file:src/estilos/estiloJuego.css");
            EncuentraPalabras.stage.setScene(scene);
        });
    }

    public VBox getRoot() {
        return root;
    }
}
