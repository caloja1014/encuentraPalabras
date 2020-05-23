/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import encuentrapalabras.EncuentraPalabras;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Usuario;
import widgets.BotonJuego;

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

    private StringBuilder palabraEscogida;
    private final Usuario usu;

    private BotonJuego[][] botones;
    private TreeMap<Integer, PriorityQueue<BotonJuego>> eliminados;

    private Queue<Temporizador> hilos;
    private int cantidad;

    public InterfazJuego(Usuario usu, int cantidad) {
        EncuentraPalabras.diccionarioPrueba.add("ssss");
        this.usu = usu;
        this.cantidad = cantidad;
        inicializar();
        agregarAcciones();
        organizarElementos();

    }

    private void organizarElementos() {

        paneJuego.setMaxSize(screenSize.height * 0.7, screenSize.height * 0.7);
        paneJuego.setPrefSize(screenSize.height * 0.7, screenSize.height * 0.7);
        paneJuego.setMinSize(screenSize.height * 0.3, screenSize.height * 0.3);

        Double particion = (screenSize.height * 0.7) / 5;
        crearYAgregarAcciones(particion, cantidad);
        informacion.getChildren().addAll(nivel, puntajePartida, imagenUsu, palabra, puntajeRecolectado);
        root.getChildren().addAll(paneJuego, informacion);
    }

    private void agregarAcciones() {
        palabra.textProperty().addListener((ObservableValue<? extends String> ob, String oldValue, String newValue) -> {
            final boolean contienePala=EncuentraPalabras.diccionarioPrueba.contains(newValue);
            
            if(contienePala){
                System.out.println(eliminados.keySet());
                palabra.setText("");
                palabraEscogida.delete(0, palabraEscogida.length());
                EncuentraPalabras.diccionarioPrueba.remove(newValue);
                for (PriorityQueue<BotonJuego> p:eliminados.values()){
                    BotonJuego botonJ=p.poll();
                    
                    while(botonJ!=null){
                        paneJuego.getChildren().remove(botonJ.getBoton());
                        botones[botonJ.getFila()][botonJ.getColumna()]=null;
                        botonJ=p.poll();
                    }
                }
            }
        });
    }
    private final Comparator<BotonJuego> comparador = (BotonJuego o1, BotonJuego o2) -> o1.getFila() - o2.getFila();

    private void inicializar() {
        hilos = new LinkedList<>();
        botones = new BotonJuego[cantidad][cantidad];
        eliminados = new TreeMap<>();
        palabraEscogida = new StringBuilder();
        root = new HBox();
        paneJuego = new AnchorPane();
        informacion = new VBox();

        nivel = new Label("Nivel " + usu.getNivel());
        puntajePartida = new Label("100");
        imagenUsu = new ImageView();
        palabra = new Label();
        puntajeRecolectado = new Label("0");
    }

    private void crearYAgregarAcciones(double particion, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < cantidad; j++) {
                BotonJuego b = new BotonJuego(particion, particion * i, particion * j, "s", j, i);
                paneJuego.getChildren().addAll(b.getBoton());
                b.getBoton().setOnAction((v) -> seleccionarBoton(b));
                botones[j][i] = b;
            }
        }

    }

    public HBox getRoot() {
        return root;
    }

    private void seleccionarBoton(BotonJuego b) {
        Temporizador t = hilos.poll();
        if (t != null) {
            t.detenerHilo();
        }
        if(eliminados.containsKey(b.getColumna())){
            eliminados.get(b.getColumna()).offer(b);
        }else{
            eliminados.put(b.getColumna(),new PriorityQueue<>(comparador) );
            eliminados.get(b.getColumna()).offer(b);
        }
        palabraEscogida.append(b.getDato());
        palabra.setText(palabraEscogida.toString());
        Temporizador tmp = new Temporizador(palabra, palabraEscogida);
        tmp.start();
        hilos.offer(tmp);
    }

}

final class Temporizador extends Thread {

    private final Label palabra;
    private final StringBuilder palabraEscogida;
    private volatile boolean detener;

    public Temporizador(Label palabra, StringBuilder palabraEscogida) {
        this.palabra = palabra;
        this.palabraEscogida = palabraEscogida;
    }

    @Override
    public void run() {

        try {

            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InterfazJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
        Platform.runLater(() -> {
            if (!detener) {
                palabraEscogida.delete(0, palabraEscogida.length());
                palabra.setText("");
            }
        });

    }

    public void detenerHilo() {
        detener = true;
    }
}
