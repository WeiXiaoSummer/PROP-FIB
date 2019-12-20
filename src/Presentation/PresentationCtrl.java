package Presentation;

import Commons.DomainLayerException;
import Commons.PresentationLayerException;
import Domain.DomainCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class is used for access the domain layer methods
 */
public class PresentationCtrl {

    /**
     * Singleton attribute for the class PresentationCtrl
     */
    private static PresentationCtrl instance = null;

    //----------------------------------------------Singleton method--------------------------------------------------//

    /**
     * Creates a new instance of the class PresentationCtrl
     */
    private PresentationCtrl() {}

    /**
     * Returns the unique PresentationCtrl instance associated with this class
     * @return
     */
    public static PresentationCtrl getInstance() {
        if (instance == null) instance = new PresentationCtrl();
        return instance;
    }

    //----------------------------------------------Singleton method--------------------------------------------------//

    //----------------------------------------Wrapped Domain Layer Methods--------------------------------------------//

    /**
     * Initializes the Domain layer.
     * @throws PresentationLayerException if an error has occurred during the initialization.
     */
    public void initializeDomainCtrl() throws PresentationLayerException {
        try {
            DomainCtrl.getInstance().initializeDomainCtrl();
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    /**
     * Compress the file or the folder indicated by inFilePath using the selected algorithm and stores the compressed saveName.PROP file
     * in the target Directory indicated by targetDirectoryPath.
     * @param inFilePath the path of the file or the folder to be compressed.
     * @param targetDirectoryPath the directory into which the compressed .PROP file is stored.
     * @param saveName the name to be saved as.
     * @param algorithmType the name of the algorithm used for compression.
     * @return a Pair which contains the compression statistics:
     *          -the key indicates the compression ratio
     *          -the value indicates the compression time
     * @throws PresentationLayerException if a error has occurred during the compression process.
     */
    public Pair<Double, Double> compress(String inFilePath, String targetDirectoryPath, String saveName, String algorithmType) throws PresentationLayerException{
        try {
            return DomainCtrl.getInstance().compress(inFilePath, targetDirectoryPath, saveName, algorithmType);
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    /**
     * Decompress the compressed .PROP file indicated by inFilePath and stores the decompressed Folder to the folder indicate by
     * targetDirectoryPath
     * @param inFilePath the .PROP file to be decompressed.
     * @param targetDirectoryPath the folder into which the decompressed folder is stored.
     * @return a Pair whice contains the decompression statistics:
     *          -the key indicates the compression ratio
     *          -the value indicates the decompression time
     * @throws PresentationLayerException if a error has occurred during the decompression process.
     */
    public Pair<Double, Double> decompress(String  inFilePath, String targetDirectoryPath) throws PresentationLayerException{
        try {
            return DomainCtrl.getInstance().decompress(inFilePath, targetDirectoryPath);
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    /**
     * Returns the statistics to be displayed.
     * @return a ObservableList which contains a group of String arrays, each of these String arrays represents an algorithm's statistic.
     */
    public ObservableList<String[]> getStatistics() {
        ObservableList<String[]> rows = FXCollections.observableArrayList();
        ArrayList<String[]> statistics = DomainCtrl.getInstance().getStatistics();
        for (String[] statistic : statistics) {
            rows.add(statistic);
        }
        return rows;
    }

    /**
     * Returns the name of the columns which should be displayed in the tableView of the statisticView which shows the global statistic
     * of each algorithm
     * @return the name of the columns which should be displayed in the tableView of the statisticView which shows the global statistic
     * of each algorithm
     */
    public String[] getStatisticColumnNames() {return DomainCtrl.getInstance().getStatisticColumnNames();}
    /**
     * Returns the name of the columns which should be displayed in the tableView of the historyView which shows the global
     * histories.
     * @return the name of the columns which should be displayed in the tableView of the historyView which shows the global
     * histories.
     */
    public ArrayList<String> getHistoryColumnNames() {
        return DomainCtrl.getInstance().getHistoryColumnNames();
    }

    /**
     * Returns the histories to be displayed.
     * @return a ObservableList which contains a group of String arrays, each of these String arrays represents a local history.
     */
    public ObservableList<String[]> getHistories() {
        ObservableList<String[]> rows = FXCollections.observableArrayList();
        ArrayList<String[]> histories = DomainCtrl.getInstance().getHistories();
        for (String[] localHistory : histories) {
            rows.add(localHistory);
        }
        return rows;
    }

    /**
     * Delete all the local histories.
     */
    public void clearHistory() { DomainCtrl.getInstance().clearHistory(); }

    /**
     * Stores important information before the program is closed
     * @throws PresentationLayerException if a occurred has occurred while trying save information.
     */
    public void closeAndSave() throws PresentationLayerException{
        try {
            DomainCtrl.getInstance().closeAndSave();
        }
        catch (DomainLayerException e) {
            throw new PresentationLayerException(e.getMessage());
        }
    }

    /**
     * Get the images for compare.
     * @param imgPath the path of .ppm image to be compared.
     * @return a Pair that contains two Images:
     *          -the key is the original Image
     *          -the value is the Image after applied the compression-decompression process
     * @throws PresentationLayerException if a error has occurred while trying get those images.
     */
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

    /**
     * Get the texts for compare
     * @param textPath the path of the .txt file to be compared.
     * @param algorithm the name of the algorithm to be used.
     * @return a Pair that contains two Strings:
     *          -the key is the original text
     *          -the value is the text after applied the compression-decompression process
     * @throws PresentationLayerException
     */
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


    /**
     * Show the notification dialog.
     * @param notificationType the type of the dialog to be showed.
     * @param title the title of the dialog to be showed.
     * @param headerText the header text of the dialog to be showed.
     * @param contentText the concrete information to be showed.
     * @param stage the stage on which the dialog is showed.
     */
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
        alert.setWidth(500d);
    }

    /**
     * Show the waiting animation in a specific scene while a specific task is taking place.
     * @param textToShow the text to be showed as a part of the waiting animation.
     * @param mainStackPane the main stack pane on which the waiting animation is showed.
     * @param paneToDisable the pane to be disabled temporally until the specific task is done.
     * @return the VBox which shows waiting animation.
     */
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

    /**
     * Deactivated the waiting animation of a specific Scene.
     * @param boxToBeDisabled the VBox to be remove.
     * @param mainStackPane the main stack pane who's waiting animation should be removed.
     * @param paneToActive the pane to be activated.
     */
    public void disableWaitingAnimationOfTheScene(VBox boxToBeDisabled, StackPane mainStackPane, Pane paneToActive) {
        mainStackPane.getChildren().remove(boxToBeDisabled);
        paneToActive.setDisable(false);
    }

    /**
     * Get the type of the file with name fileName.
     * @param fileName the name of the file.
     * @return the type of the file.
     */
    public String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex+1);
    }

    /**
     * Returns the name of the available algorithms that can be choosed for user
     * @return the name of the available algorithms that can be choosed for user, the key of the pair contains the name of the .txt compression
     * algorithm and the value contains the name of the .ppm compression algorithm.
     */
    public Pair<String[], String[]> getAlgorithms() {
        return DomainCtrl.getInstance().getAlgorithmsNames();
    }

    /**
     * Shows the compareView with images or texts indicate by toBeSet, this function should be implement in the comparisionView's controller,
     * but it will cause problems while we call it in a thread because it blocks the comparisionView, so as the second choice we implements this function
     * here.
     * @param imgs images to be compared
     * @param texts texts to be compared
     * @param toBeSet type of the content to be displayed
     * @throws PresentationLayerException
     */
    public void showContent(Pair<Image, Image> imgs, Pair<String, String> texts, String toBeSet) throws PresentationLayerException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();
            fxmlLoader.setLocation(getClass().getResource("fxml/imageComparer.fxml"));
            Pane imgpane = fxmlLoader.load();
            imageComparerController controller = fxmlLoader.getController();
            if (toBeSet.equals("ppm")) controller.setImages(imgs.getKey(), imgs.getValue());
            else controller.setText(texts.getKey(), texts.getValue());
            Scene scene = new Scene(imgpane);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            throw new PresentationLayerException("");
        }
    }
}
