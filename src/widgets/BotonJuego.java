/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widgets;

import javafx.scene.control.Button;

/**
 *
 * @author CLOJA
 */
public class BotonJuego {
    private double tamanio;
    private double pos_x;
    private double pos_y;
    private String dato;
    private boolean esPeligroso;
    private boolean esBonus;
    
    private Button boton;
    
    public BotonJuego(double tamanio, double pos_x, double pos_y, String dato) {
        this.tamanio = tamanio;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.dato = dato;
        this.esPeligroso = false;
        this.esBonus = false;
        boton = new Button(dato);
        boton.setMaxSize(tamanio, tamanio);
        boton.setPrefSize(tamanio, tamanio);
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
        return boton;
    }
    
    
}
