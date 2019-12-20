package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Platform;
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

/**
 * This is a controller Class which controls the decompression view.
 */
public class decompressionController {

    private @FXML Pane pane;
    private @FXML StackPane stackpane;
    private @FXML TextField filePath;
    private @FXML TextField directoryPath;

    /**
     * Set default behaviour when the select button is pressed:
     *      -Opens the fileChooser dialog
     *      -Writes the path of the selected file to the text field filePath
     */
    public void selectFilePressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PROP Files", "*.prop"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
        }
    }

    /**
     * Set default behaviour when the select folder button is pressed:
     *      -Opens the DirectoryChooser dialog
     *      -If selected folder isn't null writes the name of the selected folder to the text field saveName
     */
    public void selectPathPressed() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();
            directoryPath.setText(path);
        }
    }

    /**
     * Set default behaviour when the cancel button is pressed:
     *      -Clear all text fields
     */
    public void cancelPressed() {
        filePath.setText("");
        directoryPath.setText("");
    }

    /**
     * Set default behaviour when the accpt button is pressed:
     *      -Check if there is any uncompleted text field
     *      -Creates a Thread which calls the decompress method to decompress the selected file
     *      -Show decompression statistics once the compression is done
     * @throws PresentationLayerException if a error has produced during the compression
     */
    public void acceptPressed() throws PresentationLayerException {
        //Check all presentation constraints, the rest constraints such like file not found or file doesn't exists will be
        //checked in the persistence layer.
        Stage mainStage = (Stage) stackpane.getScene().getWindow();
        File inputFile = new File(filePath.getText());
        File saveDirectory = new File(directoryPath.getText());
        if (inputFile.getPath().equals("")) {
            PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Input file cannot be empty!", mainStage);
        }
        else if (saveDirectory.getPath().equals("")) {
            PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Target directory cannot be empty!", mainStage);
        }
        //decompression
        else {
            //Show the waiting animation to tell user that the compression is started
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("    Decompressing the data... \nPlease do not close the program", stackpane, pane);
            //Creates a thread which calls the decompress method
            new Thread(() -> {
                try {
                    Pair<Double, Double> decompressStatistic = PresentationCtrl.getInstance().decompress(inputFile.getPath(), saveDirectory.getPath());

                    //Show the notification dialog once the decompression is done
                    Platform.runLater(() -> PresentationCtrl.getInstance().showNotification("Information", "Information",
                            "Decompression Done!", "Compression Ratio: "+String.format("%.2f", decompressStatistic.getKey())+
                                    "\nDecompression Time: "+String.format("%.2f", decompressStatistic.getValue())+" s", mainStage));
                }
                catch (PresentationLayerException e) { throw e; }
                finally {
                    //Remove the waiting animation once the decompression is finished or some error has occurred.
                    Platform.runLater(()->{ PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackpane, pane); });
                }
            }).start();
        }
    }

}
