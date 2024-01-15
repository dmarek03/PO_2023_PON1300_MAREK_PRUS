package agh.ics.oop.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class WorldElementBox extends VBox {


    public WorldElementBox(String imagePath) {

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        getChildren().addAll(imageView);

        setAlignment(Pos.CENTER);
    }

}