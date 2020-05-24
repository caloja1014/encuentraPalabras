/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import java.util.ArrayList;

/**
 *
 * @author CLOJA
 */
public class Posicion {

    private int fila;
    private int columna;
    private ArrayList<Posicion> adyacentes;

    public Posicion(int x, int y) {
        this.adyacentes = null;
        this.fila = x;
        this.columna = y;
    }

    private ArrayList calcularAdyacentes() {
        final ArrayList<Posicion> ad= new ArrayList<>();
        final int[] filas = {1, -1, 0};
        final int[] columnas = {1, -1, 0};
        for (int f : filas) {
            for (int c : columnas) {
                if(!(f==0) || !(c==0)){
                    ad.add(new Posicion(this.fila+f,this.columna+c));
                }
            }
        }
        return ad;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public ArrayList<Posicion> getAdyacentes() {
        adyacentes=calcularAdyacentes();
        return adyacentes;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Posicion other = (Posicion) obj;
        if (this.fila != other.fila) {
            return false;
        }
        if (this.columna != other.columna) {
            return false;
        }
        return true;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Posicion{" + "fila=" + fila + ", columna=" + columna + '}';
    }

}
