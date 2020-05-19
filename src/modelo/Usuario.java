/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.scene.image.Image;

/**
 *
 * @author CLOJA
 */
public class Usuario {

    private final String nombre;
    private final String apellido;
    private String nombreCuenta;
    private String contrasena;
    private int nivel;
    private int puntaje;
    //private Image foto;

    public Usuario(
            String nombre,
            String apellido,
            String nombreCuenta,
            int nivel,
            int puntaje
            ) {
        this.nombre = nombre;
        this.apellido=apellido;
        this.nivel=nivel;
        this.nombreCuenta=nombreCuenta;
        this.puntaje=puntaje;
        
    }
    public Usuario(
            String nombre,
            String apellido,
            String nombreCuenta,
            String contrasena
            ) {
        this.contrasena=contrasena;
        this.nombre = nombre;
        this.apellido=apellido;
        this.nivel=1;
        this.nombreCuenta=nombreCuenta;
        this.puntaje=0;
        
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @return the nombreCuenta
     */
    public String getNombreCuenta() {
        return nombreCuenta;
    }

    /**
     * @param nombreCuenta the nombreCuenta to set
     */
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
/*
   
    public Image getFoto() {
        return foto;
    }

    
    public void setFoto(Image foto) {
        this.foto = foto;
    }
*/
    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", apellido=" + apellido + ", nombreCuenta=" + nombreCuenta + ", contrasena=" + contrasena + ", nivel=" + nivel + ", puntaje=" + puntaje + '}';
    }
    

}
