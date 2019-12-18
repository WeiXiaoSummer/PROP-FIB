package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
public class comparisionController {
    private @FXML StackPane stackPane;
    private @FXML Pane pane;
    private @FXML TextField filePath;

    public void selectPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt")
                , new FileChooser.ExtensionFilter("Image Files", "*.ppm"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
        }
    }

    public void cancelPressed() {
        filePath.setText("");
    }

    public void showImages(Pair<Image, Image> imgs) throws PresentationLayerException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();
            fxmlLoader.setLocation(getClass().getResource("fxml/imageComparer.fxml"));
            Pane imgpane = fxmlLoader.load();
            imageComparerController controller =fxmlLoader.getController();
            controller.setImages(imgs.getKey(), imgs.getValue());
            Scene scene = new Scene(imgpane);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            throw new PresentationLayerException("");
        }
    }

    public void acceptPressed() throws PresentationLayerException{
        String fileType = getFileType(filePath.getText());
        if (fileType.equals("ppm") | fileType.equals("txt")) {
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("      Processing the data... \nPlease do not close the program", stackPane, pane);
            new Thread(() -> {
                try {
                    if (fileType.equals("ppm")) {
                        final Pair<Image, Image> imgs = PresentationCtrl.getInstance().getImgsForCompare(filePath.getText());
                        Platform.runLater(() -> {
                            showImages(imgs);
                        });
                    }
                }
                catch (PresentationLayerException e) { throw e; }
                finally { PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackPane, pane); }
            }).start();
        }
        else {
            throw new PresentationLayerException("");
        }
    }

    private String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex+1);
    }
}
