package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class compressionController implements Initializable {

    private @FXML StackPane stackpane;
    private @FXML Pane pane;
    private @FXML RadioButton auto;
    private @FXML TextField filePath;
    private @FXML TextField directoryPath;
    private @FXML TextField saveName;
    private @FXML ToggleGroup type;

    @Override
    public void initialize(URL location, ResourceBundle resources ) {
        auto.setSelected(true);
    }


    public void selectFilePressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt")
                , new FileChooser.ExtensionFilter("Image Files", "*.ppm"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
        }
    }

    public void selectFolderPressed(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
        }
    }

    public void selectPressed(ActionEvent event) {
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
        saveName.setText("");
        auto.setSelected(true);
    }



    public void acceptPressed(ActionEvent event) throws PresentationLayerException{
        RadioButton selectedButton = (RadioButton) type.getSelectedToggle();
        String selectedAlgorithm ="";
        if (!(selectedButton == null)) selectedAlgorithm = selectedButton.getText();
        File inputFile = new File(filePath.getText());
        File saveDirectory = new File(directoryPath.getText());
        Stage mainStatge = (Stage) stackpane.getScene().getWindow();
        if (inputFile.getPath().equals("")) throw new PresentationLayerException("");//PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "input file cannot be empty",mainStatge);
        else if (saveDirectory.getPath().equals("")) throw new PresentationLayerException("");//PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "target directory cannot be empty", mainStatge);
        else if (saveName.getText().equals("")) throw new PresentationLayerException("");//PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "name cannot be empty", mainStatge);
        else if (selectedAlgorithm.equals("")) throw new PresentationLayerException("");//PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Please select a algorithm for compression", mainStatge);
        else {
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("      Compressing the data... \nPlease do not switch the pane", stackpane, pane);
            new Thread( ()-> {
                try {
                    Pair<Double, Double> compressStatistic = PresentationCtrl.getInstance().compress(inputFile.getPath(), saveDirectory.getPath(),saveName.getText(),selectedButton.getText());
                    Platform.runLater(() -> PresentationCtrl.getInstance().showNotification("Information", "Information", "Compression Done!",
                            "Compression Ratio: "+String.format("%.2f", compressStatistic.getKey())+
                                    "\nCompression Time: "+String.format("%.2f", compressStatistic.getValue())+" s", mainStatge));
                }
                catch (PresentationLayerException e) { throw e; }
                finally { Platform.runLater(() -> PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackpane, pane)); }
            }).start();
        }
    }


}
