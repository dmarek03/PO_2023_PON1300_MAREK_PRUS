package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationInitializer {

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
    private Button StopStartSimulationButton;
    private boolean isRunning =true;




    @FXML
    private void onSimulationStartClicked() throws IOException, InterruptedException {

        int time = Integer.parseInt(simTimeField.getText());
        int height = Integer.parseInt(mapHeightField.getText());
        int width = Integer.parseInt(mapWidthField.getText());
        int grassNumber = Integer.parseInt(mapGrassNumberField.getText());
        int grassEnergy = Integer.parseInt(mapGrassEnergyField.getText());
        int userGrass = Integer.parseInt(mapGrassNewField.getText());
        int animalNumber = Integer.parseInt(mapAnimalNumberField.getText());
        int startEnergy = Integer.parseInt(mapAnimalEnergyField.getText());
        int copulateEnergy = Integer.parseInt(mapAnimalCopulateEnergyField.getText());
        double creationEnergy = Double.parseDouble(mapAnimalCreateEnergyField.getText());
        int geneLength = Integer.parseInt(mapAnimalGeneField.getText());
        boolean standardPlants = standardPlant.isSelected();
        boolean corpses = lifegivingCorpses.isSelected();
        boolean standardMutations = standardMutation.isSelected();
        boolean switchMutations = switchMutation.isSelected();

//        System.out.println(time + ", " + height + ", " + width + ", " + grassNumber + ", " + grassEnergy + ", " + userGrass + ", " + animalNumber + ", " + copulateEnergy);

        double size = 0.8;
        WorldMap map = new StandardMap(width, height, grassNumber, grassEnergy, size, userGrass);

//        ConsoleMapDisplay observer = new ConsoleMapDisplay();
//        map.addObserver(observer);



        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
//        presenter.extra = true;

        presenter.setWorldMap(map);
        map.addObserver(presenter);

        configureStage(newStage, viewRoot);


        List<Vector2d> positions = new ArrayList<>();
        for (int i = 0; i < animalNumber; i++) {
            int x = (int)(Math.random()*width);
            int y = (int)(Math.random()*height);
            positions.add(new Vector2d(x,y));
        }


        Simulation sim = new Simulation(map,positions,time,geneLength, startEnergy, creationEnergy, copulateEnergy);
        presenter.sim = sim;
        presenter.presenterThread = new Thread(sim);
        Platform.runLater(newStage::show);
        presenter.presenterThread.start();


    }
    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
//        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
//        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());

        primaryStage.setMaximized(true);

//        Image image = new Image(backgroung_path);

    }



}
