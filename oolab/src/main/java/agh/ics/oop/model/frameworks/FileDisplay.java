package agh.ics.oop.model.frameworks;

import java.io.FileWriter;
import java.io.IOException;

public class FileDisplay {

    private String fileName;

    public FileDisplay(String fileName) {
        this.fileName = fileName;
    }

    public synchronized void write(String text) {


        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(text + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void clear() {

        try (FileWriter fileWriter = new FileWriter(fileName, false)) {

            System.out.println("File contents cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing file contents: " + e.getMessage());
        }
    }

}
