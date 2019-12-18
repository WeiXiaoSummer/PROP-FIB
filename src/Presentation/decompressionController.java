package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;


public class decompressionController {

    private @FXML Pane pane;
    private @FXML StackPane stackpane;
    private @FXML TextField filePath;
    private @FXML TextField directoryPath;


    public void selectFilePressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PROP Files", "*.prop"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
        }
    }

    public void selectPathPressed() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            directoryPath.setText(path);
        }
    }

    public void cancelPressed(ActionEvent event) {
        filePath.setText("");
        directoryPath.setText("");
    }

    public void acceptPressed() throws PresentationLayerException {
        Stage mainStage = (Stage) stackpane.getScene().getWindow();
        File inputFile = new File(filePath.getText());
        File saveDirectory = new File(directoryPath.getText());
        if (inputFile.getPath().equals("")) throw new PresentationLayerException("");//PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "input file cannot be empty", mainStage); }
        else if (saveDirectory.getPath().equals("")) throw new PresentationLayerException("");//PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "target directory cannot be empty", mainStage); }
        else {
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("    Decompressing the data... \nPlease do not switch the pane", stackpane, pane);
            new Thread(() -> {
                try {
                    Pair<Double, Double> decompressStatistic = PresentationCtrl.getInstance().decompress(inputFile.getPath(), saveDirectory.getPath());
                    Platform.runLater(() -> PresentationCtrl.getInstance().showNotification("Information", "Information",
                            "Decompression Done!", "Compression Ratio: "+String.format("%.2f", decompressStatistic.getKey())+
                                    "\nDecompression Time: "+String.format("%.2f", decompressStatistic.getValue())+" s", mainStage));
                }
                catch (PresentationLayerException e) { throw e; }
                finally {
                    Platform.runLater(()->{ PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackpane, pane); });
                }
            }).start();
        }
    }

}
