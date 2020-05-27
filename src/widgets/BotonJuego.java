/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widgets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import providers.Posicion;

/**
 *
 * @author CLOJA
 */
public class BotonJuego {

    private final double tamanio;
    private double pos_x;
    private volatile double pos_y;
    private String dato;
    private boolean esPeligroso;
    private boolean esBonus;
    private final Posicion posicion;
    private final Button boton;
    private boolean esEscogido;
    private StackPane stack;
    private Text text;
    private Image playI;
    private ImageView iv1;
    private Image botonPeligroso;
    public BotonJuego(double tamanio, double pos_x, double pos_y, String dato, int fila, int columna) {
        this.tamanio = tamanio;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.dato = dato;
        this.esPeligroso = false;
        this.esBonus = false;
        stack = new StackPane();
        text = new Text(dato);
        boton = new Button();
        boton.setMaxSize(tamanio - 2, tamanio - 2);
        boton.setPrefSize(tamanio - 2, tamanio - 2);
        this.posicion = new Posicion(fila, columna);
        this.esEscogido = false;

        playI = new Image("file:src/estilos/boton.png");
        botonPeligroso= new Image("file:src/estilos/boton_peligroso.png");
        iv1 = new ImageView(playI);
        
        iv1.setFitHeight(tamanio-2);
        iv1.setFitWidth(tamanio-2);
        stack.getChildren().addAll(iv1, text);
        stack.setPrefSize(tamanio-2, tamanio-2);
        stack.setMaxSize(tamanio-2, tamanio-2);
        stack.setMinSize(tamanio-2, tamanio-2);
        stack.setAlignment(Pos.CENTER);
        boton.setGraphic(stack);
    }

    public void moverBoton(int noLugares) {
        double posFinal = (noLugares * tamanio) + pos_y;
        posicion.setFila(posicion.getFila() + noLugares);
        new Thread(() -> {
            while (pos_y < posFinal) {
                Platform.runLater(() -> {
                    double posActual = boton.getTranslateY();
                    double resto = posFinal - pos_y;
                    double resultado = resto > 1 ? posActual + 1 : posActual + resto;
                    pos_y = resultado;
                    boton.setTranslateY(pos_y);
                });
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BotonJuego.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }).start();
    }

    public double getPos_x() {
        return pos_x;
    }

    public void setPos_x(double pos_x) {
        this.pos_x = pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }

    public void setPos_y(double pos_y) {
        this.pos_y = pos_y;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public boolean isEsPeligroso() {
        return esPeligroso;
    }

    public void setEsPeligroso(boolean esPeligroso) {
        this.esPeligroso = esPeligroso;
    }

    public boolean isEsBonus() {
        return esBonus;
    }

    public void setEsBonus(boolean esBonus) {
        this.esBonus = esBonus;
    }

    public Button getBoton() {

        boton.setPrefSize(tamanio-2, tamanio-2);
        boton.setTranslateX(pos_x);
        boton.setTranslateY(pos_y);
        return boton;
    }

    public boolean isEsEscogido() {
        return esEscogido;
    }

    public void setEsEscogido(boolean esEscogido) {
        this.esEscogido = esEscogido;
    }

    public int getFila() {
        return posicion.getFila();
    }

    public int getColumna() {
        return posicion.getColumna();
    }

    public Posicion getPosicion() {
        return posicion;
    }
    public void cambiarBotonPeligroso(){
        iv1.setImage(botonPeligroso);
    }
    public void cambiarBotonNormal(){
        iv1.setImage(playI);
    }
    /* @Override
    public String toString() {
        return "BotonJuego{" + "dato=" + dato + ", fila=" + posicion.getFila() + ", columna=" + posicion.getColumna() + '}';
    }*/
    @Override
    public String toString() {
        return dato;
    }

}
