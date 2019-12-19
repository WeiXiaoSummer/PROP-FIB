package Presentation;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;

public class imageComparerController {
    private @FXML StackPane stackRight;
    private @FXML StackPane stackLeft;
    private @FXML Slider sliderZoom;
    private @FXML ScrollPane spLeft;
    private @FXML ScrollPane spRight;

    public void setImages(Image original, Image modified) {
        ImageView imageLeft = new ImageView();
        ImageView imageRight = new ImageView();
        imageLeft.setImage(original);
        imageRight.setImage(modified);

        stackLeft.getChildren().add(imageLeft);
        stackRight.getChildren().add(imageRight);

        //move with mouse
        spLeft.setPannable(true);
        spRight.setPannable(true);

        stackLeft.scaleXProperty().bind(sliderZoom.valueProperty());
        stackLeft.scaleYProperty().bind(sliderZoom.valueProperty());
        stackRight.scaleXProperty().bind(sliderZoom.valueProperty());
        stackRight.scaleYProperty().bind(sliderZoom.valueProperty());

        // zoom
        stackLeft.prefWidthProperty().bind(sliderZoom.valueProperty().multiply(original.getWidth()));
        stackLeft.prefHeightProperty().bind(sliderZoom.valueProperty().multiply(original.getHeight()));
        stackRight.prefWidthProperty().bind(sliderZoom.valueProperty().multiply(modified.getWidth()));
        stackRight.prefHeightProperty().bind(sliderZoom.valueProperty().multiply(modified.getHeight()));
    }

    public void setText(String original, String modified) {
        Text textLeft = new Text();
        Text textRight = new Text();
        textLeft.setText(original);
        textRight.setText(modified);

        stackLeft.getChildren().add(textLeft);
        stackRight.getChildren().add(textRight);
    }
}
