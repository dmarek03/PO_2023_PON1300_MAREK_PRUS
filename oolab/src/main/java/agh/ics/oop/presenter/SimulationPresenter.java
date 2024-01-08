package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.view.WorldElementBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label infoLabel;

    @FXML
    private Label mapLabel;

    @FXML
    private TextField moveListTextField;

    @FXML
    private TextField simTimeField;

    @FXML
    private TextField mapHeightField;

    @FXML
    private TextField mapWidthField;

    @FXML
    private TextField mapGrassNumberField;

    @FXML
    private TextField mapGrassEnergyField;

    @FXML
    private TextField mapGrassNewField;

    @FXML
    private TextField mapAnimalNumberField;

    @FXML
    private TextField mapAnimalEnergyField;

    @FXML
    private TextField mapAnimalCopulateEnergyField;

    @FXML
    private TextField mapAnimalCreateEnergyField;

    @FXML
    private TextField mapAnimalGeneField;

    @FXML
    private CheckBox standardPlant;

    @FXML
    private CheckBox lifegivingCorpses;

    @FXML
    private CheckBox standardMutation;

    @FXML
    private CheckBox switchMutation;

    @FXML
    private Label moveDescriptionLabel;

    @FXML
    private GridPane mapGrid;

    public boolean extra;

    private WorldMap map;

    private int count = 0;

    private boolean firstStartClicked = true;

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

    private Label createLabel(int minWidth, int minHeight, String text) {
        Label label = new Label();
        label.setMinSize(minWidth, minHeight);
        label.setText(text);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public void drawMap(String message) {
        if (map != null) {
//            this.mapLabel.setText(map.toString());
//            String prevtext = infoLabel.getText();
//            infoLabel.setText(prevtext + "\n" + message);

//            System.out.println("drawing");
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



//                    mapGrid.add(new WorldElementBox("/ground.png"), i - lowerleft.getX() + 1, upperright.getY() - j);

                    Vector2d position = new Vector2d(i, j);
                    List<WorldElement> thisPlaceFirst = map.objectAt(position);
                    if (thisPlaceFirst == null) {
                        continue;
                    }
//                    System.out.println(thisPlaceFirst);
                    List<Animal> thisAnimals = new ArrayList<>();
                    for (WorldElement elem : thisPlaceFirst) {
                        if (elem.toString() != "*") {
                            thisAnimals.add((Animal) elem);
                        }
                    }
//                    System.out.println(thisAnimals);



                    WorldElement element;
                    if (thisAnimals.size() > 1) {
                        Competition viewComp = new Competition((ArrayList<Animal>) thisAnimals);
                        element = viewComp.getTheStrongestCouple().get(0);
                    } else if (thisAnimals.size() == 1) {
                        element = thisAnimals.get(0);
                    } else {
                        if (map.isOccupiedByGrass(position)) {
                            element = new Grass(position,0);
                        } else {
                            continue;
                        }
                    }

//                    String text = " ";
//                    if (element != null) {text = element.toString();}

//                    Label label = createLabel(50,50,text);
//                    label.setStyle("-fx-border-color: #794327");
//
//                    if (map.isOccupiedByGrass(position)) {
//                        label.setStyle("-fx-background-color: #277941");
//                    }



//                    mapGrid.add(label,i - lowerleft.getX() + 1,upperright.getY() - j);

                    WorldElementBox elementBox = new WorldElementBox(element.path());

                    mapGrid.add(elementBox, i - lowerleft.getX() + 1, upperright.getY() - j);
//
//                    mapGrid.add(element.toString(), i - lowerleft.getX() + 1, upperright.getY() - j);

                }
            }

        }
    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap(message);

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
            timeline.play();
        });
    }


}