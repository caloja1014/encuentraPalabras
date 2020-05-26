/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.Random;
import encuentrapalabras.EncuentraPalabras;
import static encuentrapalabras.EncuentraPalabras.screenSize;
import static encuentrapalabras.EncuentraPalabras.stage;
import java.util.LinkedList;
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
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;
import providers.Posicion;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import modelo.LecturaIdioma;

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
    private int puntajeActual;
    private StringBuilder palabraEscogida;
    private final Usuario usu;
    private int puntajeDePartida;
    private BotonJuego[][] botones;
    private TreeMap<Integer, LinkedList<BotonJuego>> eliminados;
    private final ArrayList<BotonJuego> botonesEliminados;
    private ArrayList<Posicion> movimientosPermitidos;
    private Queue<Temporizador> hilos;
    private final int cantidad;
    private final Double particion;
    private volatile List<String> letras;
    private final LetrasObtener obtenerLetras;
    private BotonesPeligrosos selecBotonesPeligrosos;
    private final String idioma;

    public InterfazJuego(Usuario usu, String idioma) {
        this.idioma = idioma;

        puntajeActual = 0;
        this.usu = usu;
        cantidad = obtenerCantidad();
        particion = (screenSize.height * 0.7) / cantidad;
        letras = new LinkedList<>();
        obtenerLetras = new LetrasObtener(letras, (int) cantidad * cantidad);
        obtenerLetras.start();
        botonesEliminados = new ArrayList<>();
        movimientosPermitidos = new ArrayList<>();

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
        paneJuego.setOnDragDetected(event -> {
            event.consume();
            paneJuego.startFullDrag();
            System.out.println("setOnDragDetected");
        });
        informacion = new VBox();

        nivel = new Label("Nivel " + usu.getNivel());
        puntajeDePartida = (usu.getNivel() * 20) + 100;
        puntajePartida = new Label(String.valueOf(puntajeDePartida));
        imagenUsu = new ImageView();
        palabra = new Label();
        puntajeRecolectado = new Label(String.valueOf(puntajeActual));
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
     * El metodo Agrega las acciones de los elementos que requieran como
     * listeners
     */
    private void agregarAcciones() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        palabra.textProperty().addListener((ObservableValue<? extends String> ob, String oldValue, String newValue) -> {
            pause.setOnFinished(value -> listenerPalabra(newValue));
            pause.playFromStart();
        });
    }

    /**
     * El metodo agrega un listener a la palabra para verificar que se encuentra
     * en el conjunto de palabras existentes
     *
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
            avanzarNivel(newValue);
            moverBotones();
            movimientosPermitidos.clear();
            botonesEliminados.clear();
        }

    }

    private void avanzarNivel(String newValue) {
        int puntaje = newValue.length() * 40;
        puntajeActual += puntaje;

        if (puntajeActual > puntajeDePartida) {
            usu.actualizarUsuario(usu.getNivel() + 1, usu.getPuntaje() + puntaje);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("¿Quieres avanzar al siguiente nivel?");
            alert.setGraphic(null);
            // clicking X also means no
            Platform.runLater(() -> {
                ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

                if (ButtonType.NO.equals(result)) {
                    PaginaPrincipal i = new PaginaPrincipal(usu, idioma);
                    Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
                    EncuentraPalabras.stage.setScene(scene);
                } else {
                    InterfazJuego i = new InterfazJuego(usu, idioma);
                    LecturaIdioma l = new LecturaIdioma(idioma);
                    EncuentraPalabras.idiomaJuego.clear();
                    EncuentraPalabras.idiomaJuego.addAll(l.getIdiomaSet());
                    Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);

                    EncuentraPalabras.stage.setScene(scene);
                }
            });
        }
        Platform.runLater(() -> {
            puntajeRecolectado.setText(String.valueOf(puntajeActual));
        });
    }

    /**
     * El metodo mover botones se activa siempre y cuando se eliminen los
     * botones
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
                    bo.getBoton().setOnDragDetected(event -> {
                        event.consume();
                        bo.getBoton().startFullDrag();
                    });
                    bo.getBoton().setOnMouseDragEntered((MouseDragEvent event) -> {
                        event.consume();

                        validarOpcionesPermitidas(bo);

                    });
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
     *
     * @param particion Tmanio del boton
     * @param cantidad Cantidad de botones que se hran for fila y columna
     */
    private void crearYAgregarAcciones(double particion, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < cantidad; j++) {
                //System.out.println("letras"+letras);
                BotonJuego b = new BotonJuego(particion, particion * i, particion * j, letras.remove(0), j, i);
                b.getBoton().setFocusTraversable(false);
                paneJuego.getChildren().addAll(b.getBoton());

                b.getBoton().setOnAction((v) -> validarOpcionesPermitidas(b));
                /**
                 * Las lineas a continuacion permiten continuar con seguimiento
                 * de los botones al arrastrar el raton
                 */

                b.getBoton().setOnDragDetected(event -> {
                    event.consume();
                    b.getBoton().startFullDrag();
                });
                b.getBoton().setOnMouseDragEntered((MouseDragEvent event) -> {
                    event.consume();

                    validarOpcionesPermitidas(b);

                });
                botones[j][i] = b;
            }
        }
        int iteraciones = (usu.getNivel() / 5) + 1;
        /**
         * Inicio de los hilos que seleccionaran las letras peligrosas
         */
        for (int i = 0; i < iteraciones; i++) {
            selecBotonesPeligrosos = new BotonesPeligrosos(botones, usu.getNivel());
            selecBotonesPeligrosos.start();
        }
    }

    /**
     * Valida que el boton seleccionado sea adyacente al boton anterior
     *
     * @param b Boton Esogido en la pantalla
     */
    private void validarOpcionesPermitidas(BotonJuego b) {
        validarBotonPeligroso(b);
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
                botonesEliminados.forEach((p) -> {
                    p.setEsEscogido(false);
                });
                movimientosPermitidos.clear();
                botonesEliminados.clear();
                eliminados.clear();
            }
        }
    }

    private void validarBotonPeligroso(BotonJuego b) {
        if (b.isEsPeligroso()) {
            int puntajeRestado = 10;

            puntajeActual -= puntajeRestado;
            if (puntajeActual < 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                alert.setHeaderText("¿Quieres volver a jugar?");
                alert.setGraphic(null);
                // clicking X also means no
                ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

                if (ButtonType.NO.equals(result)) {
                    PaginaPrincipal i = new PaginaPrincipal(usu, idioma);
                    Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);
                    EncuentraPalabras.stage.setScene(scene);
                } else {
                    InterfazJuego i = new InterfazJuego(usu, idioma);
                    LecturaIdioma l = new LecturaIdioma(idioma);
                    EncuentraPalabras.idiomaJuego.clear();
                    EncuentraPalabras.idiomaJuego.addAll(l.getIdiomaSet());
                    Scene scene = new Scene(i.getRoot(), screenSize.width * 0.7, screenSize.height * 0.7);

                    EncuentraPalabras.stage.setScene(scene);
                }
            } else {
                Platform.runLater(() -> {
                    puntajeRecolectado.setText(String.valueOf(puntajeActual));
                });
            }
        }
    }

    /**
     * El metodo se encarga de agregar los botones escogidos a un conjunto
     *
     * @param b
     */
    private void seleccionarBoton(BotonJuego b) {
        b.setEsEscogido(true);
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
        /**
         * Inicio del hilo que permite que no se borren las palabras escogidas
         * al instante que se seleccionan
         */
        Temporizador tmp = new Temporizador(palabra, palabraEscogida, botonesEliminados, movimientosPermitidos, eliminados);
        tmp.start();
        hilos.offer(tmp);
    }

    public HBox getRoot() {
        return root;
    }

    /**
     * Metodo para determinar la cantidad de bloques que habran en la pantalla
     *
     * @return Cantidad de bloques
     */
    private int obtenerCantidad() {
        int valorDefault = 5;
        int cantidadAdicional = usu.getNivel() / 5;
        return valorDefault + cantidadAdicional;

    }

}

/**
 * La clase temporizador ayuda limpiar los datos por pantalla cuando no se
 * seleccionan botones
 *
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
                botonesEliminados.forEach((p) -> {
                    p.setEsEscogido(false);
                });
                botonesEliminados.clear();
                eliminados.clear();
            }
        });

    }

    /**
     * Detiene el hilo en caso de que se quiera seleccionar otro boton, de este
     * modo no eliminara los datos mostrados por pantalla
     */
    public void detenerHilo() {
        detener = true;
    }
}

final class LetrasObtener {

    private volatile List<String> letras;
    private volatile int cantidad;

    public LetrasObtener(List letras, int cantidad) {
        this.letras = letras;
        this.cantidad = cantidad;
    }

    public void start() {
        Iterator<String> i = EncuentraPalabras.idiomaJuego.iterator();
        while (letras.size() < cantidad && i.hasNext()) {
            String[] palabra;
            palabra = i.next().split("");
            List<String> asList = Arrays.asList(palabra);
            asList.forEach((p) -> letras.add(p.toUpperCase()));

        }
    }
}

final class BotonesPeligrosos extends Thread {

    private final BotonJuego[][] botones;
    private final Random rd;
    private final int longitud;
    private final int nivel;
    private final int TIEMPO = 8;

    public BotonesPeligrosos(BotonJuego[][] botones, int nivel) {
        this.botones = botones;
        rd = new Random();
        longitud = botones.length;
        this.nivel = nivel;
        System.out.println(longitud);
    }

    @Override
    public void run() {
        while (stage.isShowing()) {
            int num1 = rd.nextInt(longitud);
            int num2 = rd.nextInt(longitud);
            BotonJuego b = botones[num1][num2];
            Platform.runLater(() -> {

                b.getBoton().setText("Peligroso");
                b.setEsPeligroso(true);

            });

            try {
                Thread.sleep((9 - nivel) * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BotonesPeligrosos.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(() -> {

                b.getBoton().setText(b.getDato());
                b.setEsPeligroso(false);

            });
        }
    }
}
