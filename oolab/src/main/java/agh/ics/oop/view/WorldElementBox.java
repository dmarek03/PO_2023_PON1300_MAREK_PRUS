package agh.ics.oop.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class WorldElementBox extends VBox {

    private ImageView imageView;
    private Label positionLabel;

    public WorldElementBox(String imagePath, String positionText) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        positionLabel = new Label(positionText);
        positionLabel.setTextAlignment(TextAlignment.CENTER);

        getChildren().addAll(imageView, positionLabel);

        setAlignment(Pos.CENTER);
    }

    public void setPositionText(String positionText) {
        positionLabel.setText(positionText);
    }
}