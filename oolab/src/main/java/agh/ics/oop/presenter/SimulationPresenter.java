//package agh.ics.oop.presenter;
//
//import agh.ics.oop.OptionsParser;
//import agh.ics.oop.Simulation;
//import agh.ics.oop.model.*;
//import agh.ics.oop.view.WorldElementBox;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.HPos;
//import javafx.geometry.Pos;
//import javafx.geometry.VPos;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.RowConstraints;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import javax.sound.midi.Soundbank;
//import java.awt.*;
//import java.io.IOException;
//import java.util.*;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class SimulationPresenter implements MapChangeListener {
//
//    @FXML
//    private Label infoLabel;
//
//    @FXML
//    private TextField moveListTextField;
//
//    @FXML
//    private Label moveDescriptionLabel;
//
//    @FXML
//    private GridPane mapGrid;
//    public boolean extra;
//
//    private WorldMap map;
//
//    private int count = 0;
//
//    public void setWorldMap(WorldMap map) {
//        this.map = map;
//    }
//
//    private void clearGrid() {
//        if (mapGrid.getChildren() != null && !mapGrid.getChildren().isEmpty()) {
//            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
//        }
//        mapGrid.getColumnConstraints().clear();
//        mapGrid.getRowConstraints().clear();
//    }
//
//    private Label createLabel(int minWidth, int minHeight, String text) {
//        Label label = new Label();
//        label.setMinSize(minWidth, minHeight);
//        label.setText(text);
//        label.setAlignment(Pos.CENTER);
//        return label;
//    }
//
//    public void drawMap() {
//        if (infoLabel != null && map != null) {
//            clearGrid();
//
//            Boundary bounds = map.getCurrentBounds();
//            Vector2d lowerleft = bounds.lowerleft();
//            Vector2d upperright = bounds.upperright().add(new Vector2d(1,1));
//
//            int height = 50;
//            int width = 50;
//            // Initialize columns and rows outside the loop
//            for (int i = lowerleft.getX(); i < upperright.getX(); i++) {
//                mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
//            }
//            for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
//                mapGrid.getRowConstraints().add(new RowConstraints(height));
//            }
//
//            for (int i = lowerleft.getX() + 1; i < upperright.getX() + 1; i++) {
//                Label label = createLabel(width,height,Integer.toString(i - 1));
//                label.setStyle("-fx-border-color: #000000");
//                mapGrid.add(label,i - lowerleft.getX(),0);
//            }
//
//            for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
//                Label label = createLabel(width,height,Integer.toString(j));
//                label.setStyle("-fx-border-color: #000000");
//                mapGrid.add(label,0,upperright.getY() - j);
//            }
//
//            Label zeroLabel = createLabel(width,height,"y\\x");
//            zeroLabel.setStyle("-fx-border-color: #000000");
//            mapGrid.add(zeroLabel,0,0);
//
//            for (int i = lowerleft.getX(); i < upperright.getX(); i++) {
//                for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
//                    Vector2d position = new Vector2d(i, j);
//                    com.google.common.base.Optional<List<WorldElement>> optElement = map.objectAt(position);
//                    //if (Optional.empty() == optElement) {continue;}
//                    WorldElement element = (WorldElement) optElement.get();
//
//                    Grass grassAt = (Grass) map.getGrasses().get(position);
////
////                    String text = " ";
////                    if (element != null) {text = element.toString();}
////
////                    Label label = createLabel(50,50,text);
////                    label.setStyle("-fx-border-color: #794327");
////
////                    if (grassAt != null) {
////                        label.setStyle("-fx-background-color: #277941");
////                    }
//
////                    mapGrid.add(label,i - lowerleft.getX() + 1,upperright.getY() - j);
//
//                    WorldElementBox elementBox = new WorldElementBox(element.path(), position.toString());
//                    if ((grassAt != null) && (element.toString() == "*")) {elementBox.setPositionText("Trawa");}
//                    else {elementBox.setPositionText("Z " + element.getPosition().toString());}
//                    mapGrid.add(elementBox, i - lowerleft.getX() + 1, upperright.getY() - j);
//
//                    infoLabel.setText("All Animals are living here!");
//                }
//            }
//
//        }
//    }
//
//
//    @Override
//    public void mapChanged(WorldMap worldMap, String message) {
//        Platform.runLater(() -> {
//            drawMap();
//            moveDescriptionLabel.setText(message);
//
//            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
//            timeline.play();
//        });
//    }
//
//
//    @FXML
//    private void onSimulationStartClicked() throws IOException, InterruptedException {
//        this.count++;
//        String moveListText = moveListTextField.getText();
//
//        List<MoveDirection> directions = OptionsParser.parse(moveListText.split(" "));
//
//        (new Thread(() -> {(new Simulation(directions, map)).run();})).start();
//
////        if ((extra) && (count % 2 == 0)) {
////
////            Stage newStage = new Stage();
////            FXMLLoader loader = new FXMLLoader();
////            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
////            BorderPane viewRoot = loader.load();
////            SimulationPresenter presenter = loader.getController();
////            presenter.extra = true;
////
////            configureStage(newStage, viewRoot);
////
////
////            AbstractWorldMap newMap;
////            if (map instanceof GrassField) {
////                int numOfGrasses = map.getGrasses().size();
////                newMap = new GrassField(numOfGrasses, false);
////                Map<Vector2d, WorldElement> newGrasses = new HashMap<>();
////                map.getGrasses().keySet().forEach(key -> {
////                    newGrasses.put((Vector2d) key,new Grass((Vector2d) key));
////                });
////                newMap.setGrasses(newGrasses);
////            } else {
////                Boundary bound = map.getCurrentBounds();
////                newMap = new RectangularMap(bound.upperright().getX() + 1,bound.upperright().getY() + 1);
////            }
////            Map<Vector2d, WorldElement> newAnimals = new HashMap<>();
////            map.getAnimals().keySet().forEach(key -> {
////                newAnimals.put((Vector2d) key,new Animal((Vector2d) key));
////            });
////            newMap.setAnimals(newAnimals);
////
////            newMap.registerObserver(presenter);
////
////            presenter.setWorldMap((WorldMap) newMap);
////
////
////            List<MoveDirection> newDirections = OptionsParser.parse(" ".split(" "));
////            new Thread(() -> {
////                new Simulation(newDirections, (WorldMap<WorldElement, Vector2d>) newMap).run();
////                Platform.runLater(() -> {
////                    newStage.show();
////                });
////            }).start();
////        }
//
//
//    }
//
//    private void configureStage(Stage stage, BorderPane viewRoot) {
//        Scene scene = new Scene(viewRoot);
//        stage.setScene(scene);
//        stage.setTitle("Simulation app");
//        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
//        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
//    }
//
//}