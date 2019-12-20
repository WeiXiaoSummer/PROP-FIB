package Presentation.comparisionView;

import Commons.PresentationLayerException;
import Presentation.PresentationCtrl;
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

public class comparisionViewController implements Initializable {
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

    /**
     * Set default behaviour when the select file button is pressed:
     *      -Opens the fileChooser dialog
     *      -Writes the path of the selected file to the text field filePath
     *      -If selected file isn't null writes the name of the selected file to the text field file path
     */
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

    /**
     * Set default behaviour when the cancel button is pressed:
     *      -Clear all text fields
     */
    public void cancelPressed() {
        filePath.setText("");
    }


    /**
     * Set default behaviour when the accept button is pressed:
     *      -Check if there is any uncompleted text field
     *      -Creates a Thread which calls the method getTXTsForCompare or getImgsForCompare to compress the selected file
     *      -Show compression statistics once the compression is done
     * @throws PresentationLayerException
     */
    public void acceptPressed() throws PresentationLayerException{
        //Get the type of the selected file
        String fileType = PresentationCtrl.getInstance().getFileType(filePath.getText());
        Stage mainStatge = (Stage) stackPane.getScene().getWindow();
        //add constraints
        if (filePath.getText().equals("")) PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Input file cannot be empty!",mainStatge);
        else {
            //Show the waiting animation to tell user that the comparision process has been started
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("      Processing the data... \nPlease do not close the program", stackPane, pane);
            new Thread(() -> {
                //Creates a thread which calls the get method according to the type of the selected file
                try {

                    //Get txts for compare
                    if (fileType.equals("txt")) {
                        Pair<String, String> txts = PresentationCtrl.getInstance().getTXTsForCompare(filePath.getText(), comboAlgo2.getValue());
                        Platform.runLater(() -> PresentationCtrl.getInstance().showContent(null, txts, "txt"));
                    }

                    //Get ppms for compare
                    else {
                        Pair<Image, Image> imgs = PresentationCtrl.getInstance().getImgsForCompare(filePath.getText());
                        Platform.runLater(() -> PresentationCtrl.getInstance().showContent(imgs, null, "ppm"));
                    }
                }
                catch (PresentationLayerException e) { throw e; }
                //Remove the waiting animation once the compare window is opened or some error has occurred.
                finally { Platform.runLater(()->PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackPane, pane)); }
            }).start();
        }
    }

}
