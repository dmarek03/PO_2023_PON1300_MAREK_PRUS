package agh.ics.oop.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileMapDisplay implements MapChangeListener {
    int allUpdates = 0;
    private static final String LOG_FILE_NAME = "map_id.log";

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        allUpdates += 1;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String new_message = dtf.format(now) + "\nMap id: " + worldMap.getId() + "\n" + "Number of update: " + allUpdates + "\n" + message + "\n" + worldMap.toString() + "\n";
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_NAME, true)) {
            fileWriter.write(new_message + "\n");
            System.out.println("Message written to " + LOG_FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
