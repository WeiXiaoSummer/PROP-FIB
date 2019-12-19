package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
import java.net.URL;
import java.util.ResourceBundle;

public class comparisionController {
    private @FXML StackPane stackPane;
    private @FXML Pane pane;
    private @FXML TextField filePath;
    private @FXML ComboBox<String> comboAlgo2;

    public void selectPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt")
                , new FileChooser.ExtensionFilter("Image Files", "*.ppm"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
            if (PresentationCtrl.getInstance().getFileType(path).equals("txt")) {
                comboAlgo2.setItems(FXCollections.observableArrayList("LZSS","LZ78"));
            }
            else {
                comboAlgo2.setItems(FXCollections.observableArrayList("JPEG"));
            }
        }
    }

    public void cancelPressed() {
        filePath.setText("");
    }

    public void showContent(String fileType) throws PresentationLayerException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();
            fxmlLoader.setLocation(getClass().getResource("fxml/imageComparer.fxml"));
            Pane imgpane = fxmlLoader.load();
            imageComparerController controller =fxmlLoader.getController();

            if (fileType.equals("ppm")){
                final Pair<Image, Image> imgs = PresentationCtrl.getInstance().getImgsForCompare(filePath.getText());
                controller.setImages(imgs.getKey(), imgs.getValue());
            }
            else {
                final Pair<String, String> texts = PresentationCtrl.getInstance().getTXTsForCompare(filePath.getText(), comboAlgo2.getValue());
                controller.setText(texts.getKey(), texts.getValue());
            }

            Scene scene = new Scene(imgpane);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            throw new PresentationLayerException("");
        }
    }

    public void acceptPressed() throws PresentationLayerException{
        String fileType = PresentationCtrl.getInstance().getFileType(filePath.getText());
        if (fileType.equals("ppm") | fileType.equals("txt")) {
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("      Processing the data... \nPlease do not close the program", stackPane, pane);
            new Thread(() -> {
                try {
                    Platform.runLater(() -> {
                        showContent(fileType);
                    });
                }
                catch (PresentationLayerException e) { throw e; }
                finally { Platform.runLater(()->PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackPane, pane)); }
            }).start();
        }
        else {
            throw new PresentationLayerException("");
        }
    }

}
