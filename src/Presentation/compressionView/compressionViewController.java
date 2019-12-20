package Presentation.compressionView;

import Commons.PresentationLayerException;
import Presentation.PresentationCtrl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

/**
 * This is a controller Class which controls the compressionView.
 */
public class compressionViewController implements Initializable {

    private @FXML StackPane stackpane;
    private @FXML Pane pane;
    private @FXML TextField filePath;
    private @FXML TextField directoryPath;
    private @FXML TextField saveName;
    private @FXML ComboBox<String> comboAlgo;
    private Pair<String[], String[]> algortihms;

    @Override
    /**
     * Initializes the compressionView when it's loaded.
     *      -Set chosed algorithm to null
     *      -Load the available algorithms
     * @param location default location
     * @param resources default resources
     */
    public void initialize(URL location, ResourceBundle resources ) {
        comboAlgo.setPromptText("");
        algortihms = PresentationCtrl.getInstance().getAlgorithms();
    }

    /**
     * Set default behaviour when the select file button is pressed:
     *      -Opens the fileChooser dialog
     *      -Writes the path of the selected file to the text field filePath
     *      -If selected file isn't null writes the name of the selected file to the text field saveName
     */
    public void selectFilePressed() {
        //Choose file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Files", "*.txt", "*.ppm"));
        File workingDirectory =new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(workingDirectory);
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null) {
            //write selected file's path to the text field filePath
            String path = file.getPath();
            filePath.setText(path);
            String fileType = PresentationCtrl.getInstance().getFileType(path);

            //add constraints, makes comboAlgo only shows corresponding algorithms depending on the type of the selected file,
            //for example if User has selected a .txt file it only can choose determinates algorithms, in our case between {AUTO, LZSS, LZ78}
            if (fileType.equals("txt")) comboAlgo.setItems(FXCollections.observableArrayList(algortihms.getKey()));
            else comboAlgo.setItems(FXCollections.observableArrayList(algortihms.getValue()));

            //set the default save name according to the name of the selected file
            saveName.setText(file.getName().substring(0,file.getName().length()-4));
        }
    }

    /**
     * Set default behaviour when the select folder button is pressed:
     *      -Opens the DirectoryChooser dialog
     *      -If selected folder isn't null writes the name of the selected folder to the text field saveName
     */
    public void selectFolderPressed() {
        //Choose folder
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File workingDirectory =new File(System.getProperty("user.dir"));
        directoryChooser.setInitialDirectory(workingDirectory);
        File file = directoryChooser.showDialog(pane.getScene().getWindow());
        if (file != null) {
            //write selected file's path to the text field filePath
            String path = file.getPath();
            filePath.setText(path);

            //set the default save name according to the name of the selected file
            saveName.setText(file.getName());
            comboAlgo.setItems(FXCollections.observableArrayList(algortihms.getKey()));
        }
    }

    /**
     * Set default behaviour when the select button is pressed:
     *      -Opens the DirectoryChooser dialog
     *      -If selected folder isn't null then writes it's path to the text field directoryPath
     */
    public void selectPressed() {
        //Choose folder
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(pane.getScene().getWindow());
        if (file != null) {
            String path = file.getPath();

            //write selected folder's path to the text field directoryPath
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
        saveName.setText("");
    }


    /**
     * Set default behaviour when the help button is pressed:
     *      -Opens a dialog to guide user
     */
    public void helpPressed() {
        Stage mainStage = (Stage)stackpane.getScene().getWindow();
        PresentationCtrl.getInstance().showNotification("Information", "Information", "To compress the folder of the file:",
                "1- Select the file or the folder to be compressed by click the select file button or the select folder button\n\n2- Select the folder to save the compressed file\n\n3- Introduce the name of the compressed file to be saved" +
                        "\n\n4- Select an algorithm for compression\n\n5- Click accept to start the compression", mainStage);
    }

    /**
     * Set default behaviour when the accpt button is pressed:
     *      -Check if there is any uncompleted text field
     *      -Creates a Thread which calls the compress method to compress the selected file
     *      -Show compression statistics once the compression is done
     * @throws PresentationLayerException if a error has produced during the compression
     */
    public void acceptPressed() throws PresentationLayerException{
        //Check all presentation constraints, the rest constraints such like file not found or file doesn't exists will be
        //checked in the persistence layer.
        String selectedAlgorithm = comboAlgo.getValue();
        File inputFile = new File(filePath.getText());
        File saveDirectory = new File(directoryPath.getText());
        Stage mainStatge = (Stage) stackpane.getScene().getWindow();
        if (inputFile.getPath().equals("")) PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Input file cannot be empty!",mainStatge);
        else if (saveDirectory.getPath().equals("")) PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Target directory cannot be empty!", mainStatge);
        else if (saveName.getText().equals("")) PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Name cannot be empty!", mainStatge);
        else if (selectedAlgorithm == null) PresentationCtrl.getInstance().showNotification("Warning", "Warning", null, "Please select a algorithm for compression!", mainStatge);
        //Compression
        else {
            //Show the waiting animation to tell user that the compression has been started
            VBox waitingAnimation = PresentationCtrl.getInstance().shwoWaitingAnimationInScene("      Compressing the data... \nPlease do not close the program", stackpane, pane);
            new Thread( ()-> {
                //Creates a thread which calls the compression method
                try {
                    Pair<Double, Double> compressStatistic = PresentationCtrl.getInstance().compress(inputFile.getPath(), saveDirectory.getPath(),saveName.getText(),selectedAlgorithm);

                    //Show the notification dialog once the compression is done
                    Platform.runLater(() -> PresentationCtrl.getInstance().showNotification("Information", "Information", "Compression Done!",
                            "Compression Ratio: "+String.format("%.2f", compressStatistic.getKey())+
                                    "\nCompression Time: "+String.format("%.2f", compressStatistic.getValue())+" s", mainStatge));
                }
                catch (PresentationLayerException e) { throw e; }
                //Remove the waiting animation once the compression is finished or some error has occurred.
                finally { Platform.runLater(() -> PresentationCtrl.getInstance().disableWaitingAnimationOfTheScene(waitingAnimation, stackpane, pane)); }
            }).start();
        }

    }


}
