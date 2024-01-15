package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.view.WorldElementBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.*;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label infoLabel;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Button StopStartSimulationButton;

    private WorldMap map;

    public Thread presenterThread;

    public Simulation sim;
    private boolean isRunning = true;

    private boolean firstDraw = true;

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private void clearGrid() {
        if (mapGrid.getChildren() != null && !mapGrid.getChildren().isEmpty()) {
            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        }
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private Button createButton(String path,int x, int y) {
        Button button = new Button();

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;");
        button.setMaxSize(50, 50);
        button.setOnAction(e -> animalButtonPressed(x, y));
        return button;
    }

    private Label createLabel(int minWidth, int minHeight, String text) {
        Label label = new Label();
        label.setMinSize(minWidth, minHeight);
        label.setText(text);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public void drawMap() {
        if (map != null) {
            clearGrid();

            Boundary bounds = map.getCurrentBounds();
            Vector2d lowerleft = bounds.lowerleft();
            Vector2d upperright = bounds.upperright().add(new Vector2d(1,1));

            int height = 50;
            int width = 50;

            for (int i = lowerleft.getX(); i < upperright.getX(); i++) {
                mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            }
            for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
                mapGrid.getRowConstraints().add(new RowConstraints(height));
            }

            for (int i = lowerleft.getX() + 1; i < upperright.getX() + 1; i++) {
                Label label = createLabel(width,height,Integer.toString(i - 1));
                label.setStyle("-fx-border-color: #000000");
                mapGrid.add(label,i - lowerleft.getX(),0);
            }

            for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
                Label label = createLabel(width,height,Integer.toString(j));
                label.setStyle("-fx-border-color: #000000");
                mapGrid.add(label,0,upperright.getY() - j);
            }

            Label zeroLabel = createLabel(width,height,"y\\x");
            zeroLabel.setStyle("-fx-border-color: #000000");
            mapGrid.add(zeroLabel,0,0);

            for (int i = lowerleft.getX(); i < upperright.getX(); i++) {
                for (int j = lowerleft.getY(); j < upperright.getY(); j++) {

                    Button currButton = createButton("/ground.png",i,j);
                    mapGrid.add(currButton, i - lowerleft.getX() + 1, upperright.getY() - j);

                    Vector2d position = new Vector2d(i, j);

                    WorldElement element;

                    if (map.isOccupiedByAnimal(position)) {
                        element = map.strongestAnimalAt(position);
                    } else if (map.isOccupiedByGrass(position)) {
                        element = map.grassAt(position);
                    } else {
                        continue;
                    }

                    currButton = createButton(element.path(),i,j);
                    mapGrid.add(currButton, i - lowerleft.getX() + 1, upperright.getY() - j);

                }
            }

        }
    }




    @Override
    public void mapChanged(WorldMap worldMap, String message) {

        String vector = message.split(" ")[0];

        if (Objects.equals(vector, "Day")) {
            Platform.runLater(() -> {
                infoLabel.setText(message);

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
                timeline.play();
            });
            System.out.println(message);
            return;
        }
//        System.out.println(vector);
        String[] xy = vector.split(",");
//        System.out.println(xy[0].substring(1) + " " + xy[1].substring(0,xy[1].length() - 1));
        int x = Integer.parseInt(xy[0].substring(1));
        int y = Integer.parseInt(xy[1].substring(0,xy[1].length() - 1));
//        System.out.println(xy[0] + " " + xy[1]);

//        System.out.println(message);

        Platform.runLater(() -> {
            if (firstDraw) {
                drawMap();
                firstDraw = false;
            } else {
                updatePosition(x,y);
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
            timeline.play();
        });
    }

    private void updatePosition(int i, int j) {

        Boundary bounds = map.getCurrentBounds();
        Vector2d lowerleft = bounds.lowerleft();
        Vector2d upperright = bounds.upperright().add(new Vector2d(1,1));


        Vector2d position = new Vector2d(i, j);

        WorldElement element;

        if (map.isOccupiedByAnimal(position)) {
            element = map.strongestAnimalAt(position);
        } else if (map.isOccupiedByGrass(position)) {
            element = map.grassAt(position);
        } else {

            Button currButton = createButton("/ground.png",i,j);
            mapGrid.add(currButton, i - lowerleft.getX() + 1, upperright.getY() - j);
            return;
        }

        Button currButton = createButton(element.path(),i,j);
        mapGrid.add(currButton, i - lowerleft.getX() + 1, upperright.getY() - j);


    }
    @FXML
    public void stopStartSimulation() {
        if (isRunning) {
            StopStartSimulationButton.setText("Start");
            sim.stop();
        } else {
            StopStartSimulationButton.setText("Stop");
            sim.resume();
        }

        isRunning = !isRunning;
    }


    public void animalButtonPressed(int x, int y) {
        System.out.println(x + " " + y);
    }







}