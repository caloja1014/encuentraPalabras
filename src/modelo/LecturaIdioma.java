/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author CLOJA
 */
public class LecturaIdioma {

    private Set idiomaSet = new TreeSet();
    private String idioma;

    public LecturaIdioma(String idioma) {
        this.idioma = idioma;
    }

    private void cargarIdioma() {
        if (idioma.equals("espanol") || idioma.equals("italiano")) {
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            try {
                System.out.println(idioma);
                archivo = new File("src/idiomas/" + idioma + ".csv");
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.equals("")) {
                        idiomaSet.add(linea);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            try {
                archivo = new File("/idiomas/" + idioma + ".csv");
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] valores = linea.split(",");
                    if (!valores[0].equals("")) {
                        idiomaSet.add(valores[0]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

    }

    /**
     * @return the idiomaSet
     */
    public Set getIdiomaSet() {
        cargarIdioma();
        return idiomaSet;
    }

    public void cambiarIdioma(String idioma) {
        if (!idioma.equals(this.idioma)) {
            this.idioma = idioma;
            idiomaSet.clear();
            cargarIdioma();
        }
    }

}
