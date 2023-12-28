//package agh.ics.oop;
//
//import agh.ics.oop.OptionsParser;
//import agh.ics.oop.Simulation;
//import agh.ics.oop.model.*;
//import agh.ics.oop.presenter.SimulationPresenter;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class SimulationApp extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        String[] args = getParameters().getRaw().toArray(new String[10]);
//        List<MoveDirection> directions = OptionsParser.parse(args);
//        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
//        double size = 0.8;
//        StandardMap map = new StandardMap(10, 10, 11, 2,UUID.randomUUID(), size);
//
//        ConsoleMapDisplay observer = new ConsoleMapDisplay();
//        map.addObserver(observer);
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
//        BorderPane viewRoot = loader.load();
//        SimulationPresenter presenter = loader.getController();
//
//        presenter.extra = false;
//
//        map.addObserver(observer);
//        presenter.setWorldMap((WorldMap) map);
//        System.out.println(map);
//        Simulation simulation = new Simulation(directions, positions, (WorldMap) map);
//
//        configureStage(primaryStage,viewRoot);
//
//        primaryStage.show();
//    }
//
//    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
//        Scene scene = new Scene(viewRoot);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Simulation app");
//        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
//        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
//    }
//}