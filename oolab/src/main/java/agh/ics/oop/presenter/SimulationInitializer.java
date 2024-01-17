package agh.ics.oop.presenter;

import agh.ics.oop.model.map.mapUtils.Vector2d;
import agh.ics.oop.simulations.Simulation;
import agh.ics.oop.model.map.StandardMap;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.simulations.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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



    private double mapWidth = 1000;
    private double mapHeight = 600;

    private SimulationEngine SE;

    public void setSE(SimulationEngine SE) {
        this.SE = SE;
    }


    @FXML
    private void onSimulationStartClicked() throws IOException, InterruptedException {

        int height = Integer.parseInt(mapHeightField.getText());
        int width = Integer.parseInt(mapWidthField.getText());
        int grassNumber = Integer.parseInt(mapGrassNumberField.getText());
        int grassEnergy = Integer.parseInt(mapGrassEnergyField.getText());
        int userGrass = Integer.parseInt(mapGrassNewField.getText());
        int animalNumber = Integer.parseInt(mapAnimalNumberField.getText());
        int startEnergy = Integer.parseInt(mapAnimalEnergyField.getText());
        int reproductionEnergy = Integer.parseInt(mapAnimalCopulateEnergyField.getText());
        double copulateEnergy = Double.parseDouble(mapAnimalCreateEnergyField.getText());
        int geneLength = Integer.parseInt(mapAnimalGeneField.getText());
        int time = Integer.parseInt(timeField.getText());
        String mapType = mapTypeChoiceBox.getValue();
        String mutationType = mutationTypeChoiceBox.getValue();
        this.mapWidth = Integer.parseInt(mapPixelWidth.getText());
        this.mapHeight = Integer.parseInt(mapPixelHeight.getText());

        double size = 0.8;
        WorldMap map = new StandardMap(width, height, grassNumber, grassEnergy, size, userGrass, copulateEnergy,reproductionEnergy, mapType);


        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxmls/simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();


        presenter.setWorldMap(map);
        map.addObserver(presenter);

        configureStage(newStage, viewRoot);


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
    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.setWidth(mapWidth);
        primaryStage.setHeight(mapHeight);

    }



}
