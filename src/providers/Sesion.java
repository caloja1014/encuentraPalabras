/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import static encuentrapalabras.EncuentraPalabras.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

/**
 *
 * @author CLOJA
 */
public class Sesion {
    private String cuenta;
    private String contrasena;
    private Usuario usuario;
    
    
    public Usuario getUser(String cuenta, String contrasena){
        try (Statement st = db.createStatement()) {
            String query = "SELECT * FROM usuario where nombreCuenta=\""+cuenta+"\" and contrasena=\""+contrasena+"\";";
            try (ResultSet rs = st.executeQuery(query)) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String nombreCuenta = rs.getString("nombreCuenta");
                    int nivel =rs.getInt("nivel");
                    int puntaje=rs.getInt("puntaje");
                    return new Usuario(nombre,apellido,nombreCuenta,nivel,puntaje) ;
                }
                else{
                    return null;
                }
                //System.out.println(rs.getArray("nombre"));
            } catch (Exception e) {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos! " + e);
        }
        return null;
    }
    public boolean createUser(String nombre, String apellido,String nomCuenta, String contrasena){
        String query=" insert into usuario values(\""+nombre+"\",\""+apellido+"\",\""+nomCuenta+"\",\""+contrasena+"\",1,0); ";
        try {
            PreparedStatement pst = db.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean verificacionCuenta(String nombreCuenta){
        try (Statement st = db.createStatement()) {
            String query = "SELECT * FROM usuario where nombreCuenta=\""+nombreCuenta+"\";";
            try (ResultSet rs = st.executeQuery(query)) {
                return rs.next();
                //System.out.println(rs.getArray("nombre"));
            } catch (Exception e) {
                System.out.println("error");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos! " + e);
            
        }
        return false;
    }
}
