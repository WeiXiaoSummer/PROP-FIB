package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;

public class comparisionController implements Initializable {
    private @FXML StackPane stackPane;
    private @FXML Pane pane;
    private @FXML TextField filePath;
    private @FXML ComboBox<String> comboAlgo2;
    private Pair<String[], String[]> algorithms;
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        algorithms = PresentationCtrl.getInstance().getAlgorithms();
    }

    /**
     * Set default behaviour when the help button is pressed:
     *      -Opens a dialog to guide user
     */
    public void helpPressed() {
        Stage mainStage = (Stage)stackPane.getScene().getWindow();
        PresentationCtrl.getInstance().showNotification("Information", "Information", "To compare the file:",
                "1- Select the file to be compared\n\n2- Select the algorithm to be used\n\n3- Click accept to start the comparision", mainStage);
    }

    public void selectPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.ppm"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
            if (PresentationCtrl.getInstance().getFileType(path).equals("txt")) {
                comboAlgo2.setItems(FXCollections.observableArrayList(algorithms.getKey()));
            }
            else {
                comboAlgo2.setItems(FXCollections.observableArrayList(algorithms.getValue()));
            }
        }
    }

    public void cancelPressed() {
        filePath.setText("");
    }


    public void acceptPressed() throws PresentationLayerException{
        String fileType = PresentationCtrl.getInstance().getFileType(filePath.getText());
        Stage mainStatge = (Stage) stackPane.getScene().getWindow();
        if (filePath.getText().equals("")) PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Input file cannot be empty!",mainStatge);
        else {
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("      Processing the data... \nPlease do not close the program", stackPane, pane);
            new Thread(() -> {
                try {
                    if (fileType.equals("txt")) {
                        Pair<String, String> txts = PresentationCtrl.getInstance().getTXTsForCompare(filePath.getText(), comboAlgo2.getValue());
                        Platform.runLater(() -> PresentationCtrl.getInstance().showContent(null, txts, "txt"));
                    }
                    else {
                        Pair<Image, Image> imgs = PresentationCtrl.getInstance().getImgsForCompare(filePath.getText());
                        Platform.runLater(() -> PresentationCtrl.getInstance().showContent(imgs, null, "ppm"));
                    }
                }
                catch (PresentationLayerException e) { throw e; }
                finally { Platform.runLater(()->PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackPane, pane)); }
            }).start();
        }
    }

}
