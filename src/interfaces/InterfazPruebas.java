/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.Properties;
import java.util.Vector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

public class InterfazPruebas extends Application {

    private short last_x = 0, last_y = 0;              // last click posistion
    private Vector lines = new Vector(256, 256);        // store the scribble
    private Properties printprefs = new Properties();  // store user preferences
    private Path path;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PrintScribble");

        Group root = new Group();
        Scene scene = new Scene(root, 400, 400);

        BorderPane bp = new BorderPane();
        VBox vb = new VBox();
        bp.setPadding(new Insets(10, 10, 10, 10));
        bp.setMinWidth(scene.getWidth());
        root.getChildren().add(bp);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Button b = new Button("Print");

                vb.getChildren().add(b);
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Printer call");
                        //print();
                    }
                });
                b.setOnDragDetected(event -> {
                    event.consume();
                    b.startFullDrag();
                });
                b.setOnMouseDragEntered((MouseDragEvent event) -> {
                    event.consume();
                    path.getElements()
                            .add(new LineTo(event.getSceneX(), event.getSceneY()));
                    System.out.println("holaaaaaa");
                });
                path = new Path();

                b.setOnMouseClicked(mouseHandler);
                //b.setOnMouseDragged(mouseHandler);
                b.setOnMouseEntered(mouseHandler);
                b.setOnMouseExited(mouseHandler);
                b.setOnMouseMoved(mouseHandler);
                b.setOnMousePressed(mouseHandler);
                b.setOnMouseReleased(e -> {
                    path.getElements().clear();

                });
            }
        }
        bp.setCenter(vb);

        root.getChildren().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                path.getElements()
                        .add(new MoveTo(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                path.getElements()
                        .add(new LineTo(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
            }

            // Here is an answer
            //path.toBack();
        }
    };

    /**
     * The main method. Create a PrintScribble() object and away we go!
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
