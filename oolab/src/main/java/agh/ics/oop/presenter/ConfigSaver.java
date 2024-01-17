package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;


import java.util.List;

public class ConfigSaver {

    @FXML
    private GridPane configsGrid;

    public Stage stage;

    public int thisWidth;
    public int thisHeight;
    public int noFiles;

    public File folder;

    int height = 20;

    public SimulationInitializer upper;

    private Canvas createCanvas(String path,int x,int y) {
        Canvas canvas = new Canvas(200, height);
        GraphicsContext GC = canvas.getGraphicsContext2D();

        GC.fillText(path, x, y);
        canvas.setOnMouseClicked(e -> configPressed(path));
        GC = null;
        System.gc();

        return canvas;
    }

    public void run() {

        configsGrid.getColumnConstraints().add(new ColumnConstraints(thisWidth));

        for (int i = 0; i < noFiles; i++) {
            configsGrid.getRowConstraints().add(new RowConstraints(height));
        }


        int row = 0;
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {

            Canvas canvas = createCanvas(fileEntry.getName(),0,10);
            configsGrid.add(canvas,0,row);
            canvas = null;
            System.gc();
            row++;
        }

        stage.show();

    }


    private void configPressed(String name) {
        // Początkowa ścieżka, z której zaczniemy przeszukiwanie
        String startingPath = "."; // "." oznacza bieżący katalog, możesz dostosować ścieżkę do własnych potrzeb

        try {
            Files.walk(Paths.get(startingPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(name))
                    .forEach(this::readAndPrintFile);
        } catch (IOException e) {
            e.printStackTrace();
            // Tutaj możesz dodać obsługę błędów, jeśli coś pójdzie nie tak
        }
    }

    private void readAndPrintFile(Path filePath) {
        System.out.println(filePath);

        int height = 0;
        int width = 0;
        int grassNumber = 0;
        int grassEnergy = 0;
        int userGrass = 0;
        int animalNumber = 0;
        int startEnergy = 0;
        int reproductionEnergy = 0;
        double copulateEnergy = 0.0;
        int geneLength = 0;
        int time = 0;
        String mapType = "";
        String mutationType = "";
        double mapWidth = 0.0;
        double mapHeight = 0.0;

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            System.out.println("file found");

            int lineNum = 0;
            String line;
            while ((line = br.readLine()) != null) {
                switch (lineNum) {
                    case 0 -> height = Integer.parseInt(line);
                    case 1 -> width = Integer.parseInt(line);
                    case 2 -> grassNumber = Integer.parseInt(line);
                    case 3 -> grassEnergy = Integer.parseInt(line);
                    case 4 -> userGrass = Integer.parseInt(line);
                    case 5 -> animalNumber = Integer.parseInt(line);
                    case 6 -> startEnergy = Integer.parseInt(line);
                    case 7 -> reproductionEnergy = Integer.parseInt(line);
                    case 8 -> copulateEnergy = Double.parseDouble(line);
                    case 9 -> geneLength = Integer.parseInt(line);
                    case 10 -> time = Integer.parseInt(line);
                    case 11 -> mapType = line;
                    case 12 -> mutationType = line;
                    case 13 -> mapWidth = Double.parseDouble(line);
                    case 14 -> mapHeight = Double.parseDouble(line);
                }

                lineNum++;
            }

            upper.mapHeight = mapHeight;
            upper.mapWidth = mapWidth;
            upper.simulationStart(height, width, grassNumber, grassEnergy, userGrass, animalNumber, startEnergy, reproductionEnergy, copulateEnergy, geneLength, time, mapType, mutationType);
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file not found");
        }
    }


}
