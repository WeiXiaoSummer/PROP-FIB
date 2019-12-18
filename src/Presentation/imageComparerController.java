package Presentation;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class imageComparerController {

    private @FXML ImageView beforeImage;
    private @FXML ImageView afterImage;
    private @FXML Pane paneBefore;
    private @FXML Pane paneAfter;
    private double mousePosX;
    private double mousePosY;

    public void setImages(Image original, Image modified) {
        beforeImage.setFitWidth(520);
        afterImage.setFitWidth(520);
        beforeImage.setFitHeight(400);
        beforeImage.setFitHeight(400);
        afterImage.setPreserveRatio(true);
        beforeImage.setPreserveRatio(true);
        beforeImage.setImage(original);
        afterImage.setImage(modified);
        Pane pane = new Pane();

        paneBefore.setOnMousePressed(e -> {
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();});

        paneBefore.setOnMouseDragged(event -> {
            paneBefore.setManaged(false);
            paneBefore.setTranslateX(event.getX() + paneBefore.getTranslateX() - 120);
            paneBefore.setTranslateY(event.getY() + paneBefore.getTranslateY() - 50);
            event.consume();
        });
    }
}
