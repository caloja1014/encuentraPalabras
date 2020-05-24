/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import encuentrapalabras.EncuentraPalabras;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import modelo.Usuario;
import widgets.BotonJuego;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import providers.Posicion;

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
    private TreeMap<Integer, LinkedList<BotonJuego>> eliminados;
    private final ArrayList<BotonJuego> botonesEliminados;
    private ArrayList<Posicion> movimientosPermitidos;
    private Queue<Temporizador> hilos;
    private final int cantidad;
    private final Double particion;
    private volatile List<String> letras;
    private final LetrasObtener obtenerLetras;

    public InterfazJuego(Usuario usu, int cantidad) {
        letras = new LinkedList<>();
        obtenerLetras = new LetrasObtener(letras);
        obtenerLetras.start();
        botonesEliminados = new ArrayList<>();
        movimientosPermitidos = new ArrayList<>();
        particion = (screenSize.height * 0.7) / cantidad;
        this.usu = usu;
        this.cantidad = cantidad;
        inicializar();
        agregarAcciones();
        organizarElementos();

    }
    /**
     * El metodo se encarga de inicializar las variables
     */
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
/**
 * El metodo organiza todos los elementos que contendra la pantalla
 */
    private void organizarElementos() {

        paneJuego.setMaxSize(screenSize.height * 0.7, screenSize.height * 0.7);
        paneJuego.setPrefSize(screenSize.height * 0.7, screenSize.height * 0.7);
        paneJuego.setMinSize(screenSize.height * 0.3, screenSize.height * 0.3);

        crearYAgregarAcciones(particion, cantidad);
        informacion.getChildren().addAll(nivel, puntajePartida, imagenUsu, palabra, puntajeRecolectado);
        root.getChildren().addAll(paneJuego, informacion);
    }
/**
 * El metodo Agrega las acciones de los elementos que requieran como listeners
 */
    private void agregarAcciones() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        palabra.textProperty().addListener((ObservableValue<? extends String> ob, String oldValue, String newValue) -> {
            pause.setOnFinished(value -> listenerPalabra(newValue));
            pause.playFromStart();
        });
    }
/**
 * El metodo agrega un listener a la palabra para verificar que se encuentra en el conjunto de palabras existentes
 * @param newValue Es la nueva palabra que elige la persona
 */
    private void listenerPalabra(String newValue) {
        final boolean contienePala = EncuentraPalabras.idiomaJuego.contains(newValue);
        if (contienePala) {
            obtenerLetras.start();
            palabra.setText("");
            palabraEscogida.delete(0, palabraEscogida.length());
            EncuentraPalabras.idiomaJuego.remove(newValue);
            eliminados.values().forEach((p) -> {
                p.forEach((botonJ) -> {
                    paneJuego.getChildren().remove(botonJ.getBoton());
                    botones[botonJ.getFila()][botonJ.getColumna()] = null;
                });
            });
            moverBotones();
            movimientosPermitidos.clear();
            botonesEliminados.clear();
        }

    }
/**
 * El metodo mover botones se activa siempre y cuando se eliminen los botones
 */
    private void moverBotones() {
        Set<Integer> columnas = eliminados.keySet();
        columnas.forEach((j) -> {
            int contador = 0;
            for (int i = 4; i >= 0; i--) {
                BotonJuego b = botones[i][j];
                if (b == null) {
                    contador++;
                } else {
                    if (contador != 0) {
                        botones[i][j] = null;
                        b.moverBoton(contador);
                        botones[i + contador][j] = b;
                        i = i + contador;
                        contador = 0;
                    }
                }
            }
            if (contador > 0) {
                obtenerLetras.start();

                for (int i = 0; i < contador; i++) {
                    BotonJuego bo = new BotonJuego(particion, particion * j, -particion * (i + 1), letras.remove(0), -1 - i, j);
                    bo.getBoton().setOnAction((v) -> validarOpcionesPermitidas(bo));
                    bo.getBoton().setFocusTraversable(false);
                    paneJuego.getChildren().add(bo.getBoton());
                    botones[contador - 1 - i][j] = bo;
                    bo.moverBoton(contador);

                }
            }
        });
        eliminados.clear();
        for (int i = 0; i < 5; i++) {
        }
    }
/**
 * El metodo crea los botones con su respectivo tamanio y cantidad
 * @param particion Tmanio del boton
 * @param cantidad  Cantidad de botones que se hran for fila y columna
 */
    private void crearYAgregarAcciones(double particion, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < cantidad; j++) {
                BotonJuego b = new BotonJuego(particion, particion * i, particion * j, letras.remove(0), j, i);
                b.getBoton().setFocusTraversable(false);
                paneJuego.getChildren().addAll(b.getBoton());
                b.getBoton().setOnAction((v) -> validarOpcionesPermitidas(b));

                botones[j][i] = b;
            }
        }

    }
/**
 * Valida que el boton seleccionado sea adyacente al boton anterior
 * @param b  Boton Esogido en la pantalla
 */
    private void validarOpcionesPermitidas(BotonJuego b) {
        if (movimientosPermitidos.isEmpty() && !botonesEliminados.contains(b)) {
            movimientosPermitidos = b.getPosicion().getAdyacentes();
            seleccionarBoton(b);
        } else {
            if (movimientosPermitidos.contains(b.getPosicion()) && !botonesEliminados.contains(b)) {
                movimientosPermitidos = b.getPosicion().getAdyacentes();
                seleccionarBoton(b);

            } else {
                palabra.setText("");
                palabraEscogida.delete(0, palabraEscogida.length());
                movimientosPermitidos.clear();
                botonesEliminados.clear();
                eliminados.clear();
            }
        }
    }
/**
 * El metodo se encarga de agregar los botones escogidos a un conjunto
 * @param b 
 */
    private void seleccionarBoton(BotonJuego b) {
        botonesEliminados.add(b);
        Temporizador t = hilos.poll();
        if (t != null) {
            t.detenerHilo();
        }
        if (eliminados.containsKey(b.getColumna())) {
            eliminados.get(b.getColumna()).offer(b);
        } else {
            eliminados.put(b.getColumna(), new LinkedList<>());
            eliminados.get(b.getColumna()).offer(b);
        }
        palabraEscogida.append(b.getDato());
        palabra.setText(palabraEscogida.toString());
        Temporizador tmp = new Temporizador(palabra, palabraEscogida, botonesEliminados, movimientosPermitidos, eliminados);
        tmp.start();
        hilos.offer(tmp);
    }

    public HBox getRoot() {
        return root;
    }

}
/**
 * La clase temporizador ayuda limpiar los datos por pantalla cuando no se seleccionan botones
 * @author CLOJA
 */
final class Temporizador extends Thread {

    private final Label palabra;
    private final StringBuilder palabraEscogida;
    private volatile boolean detener;
    private final ArrayList<BotonJuego> botonesEliminados;
    private final ArrayList<Posicion> movimientosPermitidos;
    private TreeMap<Integer, LinkedList<BotonJuego>> eliminados;

    public Temporizador(Label palabra, StringBuilder palabraEscogida, ArrayList<BotonJuego> botonesEliminados, ArrayList<Posicion> movimientosPermitidos, TreeMap<Integer, LinkedList<BotonJuego>> eliminados) {
        this.palabra = palabra;
        this.palabraEscogida = palabraEscogida;
        this.botonesEliminados = botonesEliminados;
        this.movimientosPermitidos = movimientosPermitidos;
        this.eliminados = eliminados;
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
                movimientosPermitidos.clear();
                botonesEliminados.clear();
                eliminados.clear();
            }
        });

    }
/**
 * Detiene el hilo en caso de que se quiera seleccionar otro boton, de este modo no eliminara los datos mostrados por pantalla
 */
    public void detenerHilo() {
        detener = true;
    }
}

final class LetrasObtener {

    private volatile List<String> letras;

    public LetrasObtener(List letras) {
        this.letras = letras;
    }

    public void start() {
        Iterator<String> i = EncuentraPalabras.idiomaJuego.iterator();
        while (letras.size() < 25 && i.hasNext()) {
            String[] palabra;
            palabra = i.next().split("");
            List<String> asList = Arrays.asList(palabra);
            
            letras.addAll(asList);
        }
        Collections.shuffle(letras);

    }

}
