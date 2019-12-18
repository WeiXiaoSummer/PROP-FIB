package Presentation;

import Commons.DomainLayerException;
import Commons.PresentationLayerException;
import Domain.DomainCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class PresentationCtrl {

    private static PresentationCtrl instance = null;

    private PresentationCtrl() {}

    public static PresentationCtrl getInstance() {
        if (instance == null) instance = new PresentationCtrl();
        return instance;
    }

    public void initializeDomainCtrl() throws PresentationLayerException {
        try {
            DomainCtrl.getInstance().initializeDomainCtrl();
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    public Pair<Double, Double> compress(String inFilePath, String targetDirectoryPath, String saveName, String algorithmType) throws PresentationLayerException{
        try {
            return DomainCtrl.getInstance().compress(inFilePath, targetDirectoryPath, saveName, algorithmType);
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    public Pair<Double, Double> decompress(String  inFilePath, String targetDirectoryPath) throws PresentationLayerException{
        try {
            return DomainCtrl.getInstance().decompress(inFilePath, targetDirectoryPath);
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    public ArrayList<double[]> getStatistics() {
        return DomainCtrl.getInstance().getStatistics();
    }

    public ArrayList<String> getHistoryColumnNames() {
        return DomainCtrl.getInstance().getHistoryColumnNames();
    }

    public ObservableList<String[]> getHistories() {
        ObservableList<String[]> rows = FXCollections.observableArrayList();
        ArrayList<String[]> histories = DomainCtrl.getInstance().getHistories();
        for (String[] localHistory : histories) {
            rows.add(localHistory);
        }
        return rows;
    }

    public void clearHistory() { DomainCtrl.getInstance().clearHistory(); }
    public void closeAndSave() throws PresentationLayerException{
        try {
            DomainCtrl.getInstance().closeAndSave();
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    public Pair<Image, Image> getImgsForCompare(String imgPath) throws PresentationLayerException{
        try {
            Pair<byte[], byte[]> imgContents = DomainCtrl.getInstance().getCompareFilesContent(imgPath, "JPEG");
            byte[] originalContent = imgContents.getKey();
            byte[] decompressedContent = imgContents.getValue();
            int width = 0;
            int height = 0;
            int pos = 3;
            while (originalContent[pos] != ' ') {
                width = width * 10 + (originalContent[pos] - '0');
                ++pos;
            }
            ++pos;
            while (originalContent[pos] != '\n') {
                height = height * 10 + (originalContent[pos] - '0');
                ++pos;
            }
            int offset = pos + 5;
            WritableImage originalImage = new WritableImage(width, height);
            WritableImage decompressedImage = new WritableImage(width, height);
            PixelWriter originalPixelWriter = originalImage.getPixelWriter();
            PixelWriter decompresedPixelWriter = decompressedImage.getPixelWriter();

            for (int i = 0; i < height; ++i) {
                int xPos = i * width*3 + offset;
                for (int j = 0; j < width; ++j) {
                    int firstPixelPos = j * 3 + xPos;
                    Color originalColor = Color.rgb(originalContent[firstPixelPos]&0xff, originalContent[firstPixelPos+1]&0xff, originalContent[firstPixelPos+2]&0xff);
                    Color decompressedColor = Color.rgb(decompressedContent[firstPixelPos]&0xff, decompressedContent[firstPixelPos+1]&0xff, decompressedContent[firstPixelPos+2]&0xff);
                    originalPixelWriter.setColor(j, i, originalColor);
                    decompresedPixelWriter.setColor(j, i, decompressedColor);
                }
            }
            return new Pair<>(originalImage, decompressedImage);
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    public Pair<String, String> getTXTsForCompare(String textPath, String algorithm) throws PresentationLayerException{
        try {
            Pair<byte[], byte[]> textContents = DomainCtrl.getInstance().getCompareFilesContent(textPath, algorithm);
            String originalContent = new String(textContents.getKey());
            String decompressedContent = new String(textContents.getValue());
            return new Pair<>(originalContent, decompressedContent);
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }

    }


    public void showNotification(String notificationType, String title, String headerText, String contentText, Stage stage) {
        Alert alert;
        switch (notificationType) {
            case "Warning":
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            case "Information":
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            default:
                alert = new Alert(Alert.AlertType.ERROR);
                break;
        }
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initOwner(stage);
        alert.setX(alert.getX()+(stage.getWidth()/2-380d));
        alert.show();
    }

    public VBox shwoWaitingAnimationInScene(String textToShow, StackPane mainStackPane, Pane paneToDisable) {
        ProgressIndicator indicator = new ProgressIndicator();
        Label text = new Label();
        text.setText(textToShow);
        VBox box = new VBox(indicator, text);
        box.setAlignment(Pos.CENTER);
        paneToDisable.setDisable(true);
        mainStackPane.getChildren().add(box);
        mainStackPane.setAlignment(box, Pos.CENTER);
        return box;
    }

    public void disableWaitingAnimationOfTheScene(VBox boxToBeDisabled, StackPane mainStackPane, Pane paneToActive) {
        mainStackPane.getChildren().remove(boxToBeDisabled);
        paneToActive.setDisable(false);
    }
}
