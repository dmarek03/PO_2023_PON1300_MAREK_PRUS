package agh.ics.oop.presenter;

import agh.ics.oop.model.frameworks.FileDisplay;
import agh.ics.oop.model.map.mapUtils.Vector2d;
import agh.ics.oop.simulations.Simulation;
import agh.ics.oop.model.map.StandardMap;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.simulations.SimulationEngine;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimulationInitializer {


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
    private TextField timeField;


    @FXML
    private ChoiceBox<String> mapTypeChoiceBox;

    @FXML
    private ChoiceBox<String> mutationTypeChoiceBox;

    @FXML
    private TextField mapPixelWidth;

    @FXML
    private TextField mapPixelHeight;

    @FXML
    private TextField configName;



    public double mapWidth = 1000;
    public double mapHeight = 600;

    private SimulationEngine SE;

    public void setSE(SimulationEngine SE) {
        this.SE = SE;
    }


    private static String text = "";

    @FXML
    private void onSimulationStartClicked() throws IOException {

        int height = Integer.parseInt(mapHeightField.getText());
        this.text += height;
        int width = Integer.parseInt(mapWidthField.getText());
        this.text += "\n" + width;
        int grassNumber = Integer.parseInt(mapGrassNumberField.getText());
        this.text += "\n" + grassNumber;
        int grassEnergy = Integer.parseInt(mapGrassEnergyField.getText());
        this.text += "\n" + grassEnergy;
        int userGrass = Integer.parseInt(mapGrassNewField.getText());
        this.text += "\n" + userGrass;
        int animalNumber = Integer.parseInt(mapAnimalNumberField.getText());
        this.text += "\n" + animalNumber;
        int startEnergy = Integer.parseInt(mapAnimalEnergyField.getText());
        this.text += "\n" + startEnergy;
        int reproductionEnergy = Integer.parseInt(mapAnimalCopulateEnergyField.getText());
        this.text += "\n" + reproductionEnergy;
        double copulateEnergy = Double.parseDouble(mapAnimalCreateEnergyField.getText());
        this.text += "\n" + copulateEnergy;
        int geneLength = Integer.parseInt(mapAnimalGeneField.getText());
        this.text += "\n" + geneLength;
        int time = Integer.parseInt(timeField.getText());
        this.text += "\n" + time;
        String mapType = mapTypeChoiceBox.getValue();
        this.text += "\n" + mapType;
        String mutationType = mutationTypeChoiceBox.getValue();
        this.text += "\n" + mutationType;
        this.mapWidth = Integer.parseInt(mapPixelWidth.getText());
        this.text += "\n" + this.mapWidth;
        this.mapHeight = Integer.parseInt(mapPixelHeight.getText());
        this.text += "\n" + this.mapHeight;

        simulationStart(height, width, grassNumber, grassEnergy, userGrass, animalNumber, startEnergy, reproductionEnergy, copulateEnergy, geneLength, time, mapType, mutationType);


    }

    public void simulationStart(int height, int width, int grassNumber, int grassEnergy, int userGrass, int animalNumber, int startEnergy, int reproductionEnergy, double copulateEnergy, int geneLength, int time, String mapType, String mutationType) throws IOException {
        double size = 0.8;

        WorldMap map = new StandardMap(width, height, grassNumber, grassEnergy, size, userGrass, copulateEnergy,reproductionEnergy, mapType);


        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxmls/simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();


        presenter.setWorldMap(map);
        map.addObserver(presenter);

        configureStage(newStage, viewRoot, (int)mapWidth, (int)mapHeight, "Simulation App");


        presenter.setMeasures(new Vector2d((int)mapWidth - 300,(int)mapHeight - 50));


        List<Vector2d> positions = new ArrayList<>();
        for (int i = 0; i < animalNumber; i++) {
            int x = (int)(Math.random()*width);
            int y = (int)(Math.random()*height);
            positions.add(new Vector2d(x,y));
        }

        Simulation sim = new Simulation(map,positions,geneLength, startEnergy, copulateEnergy, reproductionEnergy, mutationType, time);
        presenter.sim = sim;
        Thread simThread = new Thread(sim);
        presenter.presenterThread = simThread;


        SE.addThread(simThread);

        Platform.runLater(newStage::show);
        presenter.presenterThread.start();
    }

    private static Stage acceptStage;

    @FXML
    private void onSaveClicked() throws IOException {
        acceptStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxmls/newConfig.fxml"));
        BorderPane viewRoot = loader.load();
//        ConfigSaver confSave = loader.getController();

        configureStage(acceptStage, viewRoot,250, 150,"Config Name");

        Platform.runLater(acceptStage::show);

    }

    @FXML
    private void onAcceptClicked() {
        acceptStage.close();
        FileDisplay displayer = new FileDisplay("oolab/mapConfigs/" + configName.getText() + ".txt");
        displayer.write(text);
        System.out.println(this.text);
    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot,int width, int height, String name) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle(name);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }


    @FXML
    private static Stage loadStage;

    @FXML
    public void onLoadClicked() throws IOException {

        int noFiles = 0;

        final File folder = new File("oolab/mapConfigs");
        for (final File fileEntry : folder.listFiles()) {
            noFiles++;
        }

        int thisWidth = 250;
        int thisHeight = 100*noFiles;

        loadStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxmls/configLoad.fxml"));
        BorderPane viewRoot = loader.load();
        ConfigSaver confSave = loader.getController();
        confSave.stage = loadStage;
        confSave.thisWidth = thisWidth;
        confSave.thisHeight = thisHeight/noFiles;
        confSave.noFiles = noFiles;
        confSave.folder = folder;
        confSave.upper = this;



        configureStage(loadStage, viewRoot,thisWidth, thisHeight,"Load Config");
        confSave.run();

        Platform.runLater(loadStage::show);

    }

}
