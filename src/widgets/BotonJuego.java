/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widgets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;

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
    private int fila;
    private int columna;
    private final Button boton;
    private boolean esEscogido;

    public BotonJuego(double tamanio, double pos_x, double pos_y, String dato, int fila, int columna) {
        this.tamanio = tamanio;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.dato = dato;
        this.esPeligroso = false;
        this.esBonus = false;
        boton = new Button(dato);
        boton.setMaxSize(tamanio, tamanio);
        boton.setPrefSize(tamanio, tamanio);
        this.fila = fila;
        this.columna = columna;
        this.esEscogido = false;
    }

    public void moverBoton(int noLugares) {
        double posFinal = (noLugares * tamanio) + pos_y;
        fila += noLugares;
        new Thread(() -> {
            while (pos_y < posFinal) {
                Platform.runLater(() -> {
                    double posActual = boton.getTranslateY();
                    double resto= posFinal-pos_y;
                    double resultado=resto>1?posActual + 1:posActual + resto;
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
        boton.setPrefSize(tamanio, tamanio);
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
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "BotonJuego{" + "dato=" + dato + ", fila=" + fila + ", columna=" + columna + '}';
    }

}
