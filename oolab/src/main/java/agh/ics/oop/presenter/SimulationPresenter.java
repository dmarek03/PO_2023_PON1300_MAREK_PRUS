package agh.ics.oop.presenter;

import agh.ics.oop.model.map.mapUtils.Vector2d;
import agh.ics.oop.simulations.Simulation;
import agh.ics.oop.statistics.Statistics;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.frameworks.FileDisplay;
import agh.ics.oop.model.map.mapUtils.Boundary;
import agh.ics.oop.model.frameworks.MapChangeListener;
import agh.ics.oop.model.map.StandardMap;
import agh.ics.oop.model.map.WorldMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

import java.util.*;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label infoLabel;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label animalStatInfo = new Label();

    @FXML
    private Label mapStatInfo;

    private WorldMap map;

    public Thread presenterThread;

    public Simulation sim;

    private boolean firstDraw = true;

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private int cellWidth = 0;
    private int cellHeight = 0;

    private Animal observedAnimal = null;

    private boolean mapStatsVisibility = false;

    private List<Animal> bestGenome = new ArrayList<>();

    private List<Vector2d> grassPreffered = new ArrayList<>();
    private List<Vector2d> oldGrassPreffered = new ArrayList<>();


    private FileDisplay displayer;

    public void setMeasures(Vector2d measures) {

        int sceneWidth = measures.getX();
        int sceneHeight = measures.getY();

        Boundary bounds = map.getCurrentBounds();

        int horCells = Math.abs(bounds.upperright().getX() - bounds.lowerleft().getX()) + 2;
//        System.out.println(horCells);
        int vertCells = Math.abs(bounds.upperright().getY() - bounds.lowerleft().getY()) + 2;
        int min = Math.min(((sceneWidth) / (horCells)), ((sceneHeight) / (vertCells)));
        this.cellWidth = min;
        this.cellHeight = min;

//        System.out.println("Measures Initialized " + cellWidth + " " + cellHeight);

    }



    private void clearGrid() {
        if (mapGrid.getChildren() != null && !mapGrid.getChildren().isEmpty()) {
            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        }
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private Canvas createCanvas(String path, int x, int y) {
        Canvas canvas = new Canvas(cellWidth, cellHeight);
        GraphicsContext GC = canvas.getGraphicsContext2D();

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        GC.drawImage(image, 0, 0, cellWidth, cellHeight);
        image = null;
        GC = null;
        System.gc();
        canvas.setOnMouseClicked(e -> animalButtonPressed(x, y));

        return canvas;
    }


    private Label createLabel(int minWidth, int minHeight, String text, String color) {
        Label label = new Label();
        label.setMinSize(minWidth, minHeight);
        label.setText(text);
        label.setAlignment(Pos.CENTER);
        label.setStyle(color);
        return label;
    }

    public void drawMap() {
        if (map != null) {
            clearGrid();

            Boundary bounds = map.getCurrentBounds();
            Vector2d lowerleft = bounds.lowerleft();
            Vector2d upperright = bounds.upperright().add(new Vector2d(1,1));


            int height = cellHeight;
            int width = cellWidth;

            for (int i = lowerleft.getX(); i < upperright.getX(); i++) {
                mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            }
            for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
                mapGrid.getRowConstraints().add(new RowConstraints(height));
            }

            for (int i = lowerleft.getX() + 1; i < upperright.getX() + 1; i++) {
                Label label = createLabel(width,height,Integer.toString(i - 1),"-fx-border-color: #000000");
                mapGrid.add(label,i - lowerleft.getX(),0);
                label = null;
                System.gc();
            }

            for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
                Label label = createLabel(width,height,Integer.toString(j),"-fx-border-color: #000000");
                mapGrid.add(label,0,upperright.getY() - j);
                label = null;
                System.gc();
            }

            Label zeroLabel = createLabel(width,height,"y\\x","-fx-border-color: #000000");
            mapGrid.add(zeroLabel,0,0);
            zeroLabel = null;
            System.gc();

            for (int i = lowerleft.getX(); i < upperright.getX(); i++) {
                for (int j = lowerleft.getY(); j < upperright.getY(); j++) {
                    updatePosition(i,j);
                }
            }

        }
    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {

        String vector = message.split(" ")[0];

        this.grassPreffered = map.getPreferred();

        if (Objects.equals(vector, "Day")) {
            Platform.runLater(() -> {
                infoLabel.setText(message);

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
                timeline.play();
            });
            return;
        }

        if (Objects.equals(vector, "Animal")) {
            Platform.runLater(() -> {
                updateAnimalInfo();

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
                timeline.play();
            });
            return;
        }
        String[] xy = vector.split(",");
        int x = Integer.parseInt(xy[0].substring(1));
        int y = Integer.parseInt(xy[1].substring(0,xy[1].length() - 1));

        Platform.runLater(() -> {
            if (firstDraw) {
                displayer = new FileDisplay("oolab/mapLogs/" + map.getId().toString() + ".csv");
                drawMap();
                firstDraw = false;
            } else {
                updatePosition(x,y);
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
            timeline.play();
        });

        if (mapStatsVisibility) {
            Platform.runLater(() -> {
                Statistics stat = new Statistics((StandardMap) map);
                mapStatInfo.setText(stat.showMapStats() + "\n\n====================\n");
                bestGenome = stat.getAnimalsWithTheMostPopularGenotype();

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {}));
                timeline.play();
            });
        }

    }


    private void clearGridCell(int col, int row) {
        List<Node> toRemove = new ArrayList<>();

        for (Node node : mapGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                toRemove.add(node);
            }
        }

        mapGrid.getChildren().removeAll(toRemove);
    }


    private void updatePosition(int i, int j) {

        Boundary bounds = map.getCurrentBounds();
        Vector2d lowerleft = bounds.lowerleft();
        Vector2d upperright = bounds.upperright().add(new Vector2d(1,1));


        Vector2d position = new Vector2d(i, j);

        WorldElement element;


        clearGridCell(i - lowerleft.getX() + 1,upperright.getY() - j);
        Canvas canvas = createCanvas("/fields/ground.png", i, j);
        mapGrid.add(canvas, i - lowerleft.getX() + 1, upperright.getY() - j);


        if ((grassPreffered.contains(position)) && (mapStatsVisibility)) {
            canvas = createCanvas("/masks/grassPreferred.png", i, j);
            mapGrid.add(canvas, i - lowerleft.getX() + 1, upperright.getY() - j);
        }


        if (map.isOccupiedByAnimal(position)) {
            element = map.strongestAnimalAt(position);
        } else if (map.isOccupiedByGrass(position)) {
            element = map.grassAt(position);
        } else {
            canvas = null;
            System.gc();
            return;
        }
        canvas = createCanvas(element.path(), i, j);
        mapGrid.add(canvas, i - lowerleft.getX() + 1, upperright.getY() - j);
        if (map.isOccupiedByAnimal(position)) {
            Animal animal = (Animal) element;

            Statistics mapStat = new Statistics((StandardMap) map);
            bestGenome = mapStat.getAnimalsWithTheMostPopularGenotype();

            if ((mapStatsVisibility) && (bestGenome.contains(animal))) {
                canvas = createCanvas("/masks/bestGenome.png", i, j);
                mapGrid.add(canvas, i - lowerleft.getX() + 1, upperright.getY() - j);
            }
            if (animal == this.observedAnimal) {
                canvas = createCanvas("/masks/chosenAnimal.png", i, j);
                mapGrid.add(canvas, i - lowerleft.getX() + 1, upperright.getY() - j);
            }

            canvas = createCanvas(animal.healthPath(), i, j);
            mapGrid.add(canvas, i - lowerleft.getX() + 1, upperright.getY() - j);
        }
        canvas = null;
        System.gc();

    }


    public void onStartSimulationButtonClicked() {
        sim.startSimulation();
    }

    public void onStopSimulationButtonClicked() {
        sim.stopSimulation();
    }


    public void onSaveStatsButtonClicked() {
//        System.out.println("Here will be saving stats");

        String text = "";

        if (mapStatsVisibility) {
            text += mapStatInfo.getText();
        }
        if (observedAnimal != null) {
            text += "\n" + animalStatInfo.getText() + "\n\n====================\n\n";
        }

        if (!text.equals("")) {
            displayer.write(text);
        } else {
            mapStatInfo.setText("NO STATS TO SAVE!!!");
        }

    }


    public void animalButtonPressed(int x, int y) {
//        System.out.println(x + " " + y);
        Vector2d position = new Vector2d(x,y);
        if (!map.isOccupiedByAnimal(position)) {
            this.observedAnimal = null;
            animalStatInfo.setText("");
            return;
        }

        this.observedAnimal = map.strongestAnimalAt(position);

        Statistics stat = new Statistics(observedAnimal);

        animalStatInfo.setText(stat.showAnimalStatistics());

    }

    public void updateAnimalInfo() {
        if (this.observedAnimal == null) {return;}
        Statistics stat = new Statistics(observedAnimal);

        animalStatInfo.setText(stat.showAnimalStatistics());
    }


    public void onClickStatsToggle() {
        if (!mapStatsVisibility) {
            Statistics stat = new Statistics((StandardMap) map);
            oldGrassPreffered = grassPreffered;
            mapStatInfo.setText(stat.showMapStats() + "\n--------------------\n");
            mapStatsVisibility = true;
        } else {
            mapStatInfo.setText("");
            mapStatsVisibility = false;
        }

        List<Vector2d> sumGrassPreffered = new ArrayList<>();

        for (Vector2d position :  oldGrassPreffered) {
            if (sumGrassPreffered.contains(position)) {continue;}
            sumGrassPreffered.add(position);
        }
        for (Vector2d position :  grassPreffered) {
            if (sumGrassPreffered.contains(position)) {continue;}
            sumGrassPreffered.add(position);
        }
        for (Vector2d position :  sumGrassPreffered) {
            mapChanged(map,position.toString());
        }



    }


}