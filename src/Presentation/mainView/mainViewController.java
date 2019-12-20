package Presentation.mainView;

import Commons.PresentationLayerException;
import Presentation.PresentationCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is a controller class which controls the mainView
 */
public class mainViewController implements Initializable {
    private @FXML BorderPane borderpane;
    private @FXML StackPane compressionView;
    private @FXML StackPane decompressionView;
    private @FXML Pane statisticView;
    private @FXML Pane historyView;
    private @FXML StackPane comparisionView;

    /**
     * Initializes the mainView when it's loaded
     * @param location default location
     * @param resources default resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            compressionView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Presentation/compressionView/compressionView.fxml")));
            decompressionView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Presentation/decompressionView/decompressionView.fxml")));
            statisticView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Presentation/statisticView/statisticView.fxml")));
            historyView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Presentation/historyView/historyView.fxml")));
            comparisionView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Presentation/comparisionView/comparisionView.fxml")));
            borderpane.setCenter(compressionView);
        } catch (IOException e) {
            Stage mainStage = (Stage) borderpane.getScene().getWindow();
            PresentationCtrl.getInstance().showNotification("Error", "Error", "Cannot load stage",
                    "An error has occured while trying to load the stage, program aborted. \n\n" +
                    "See below\n\n"+e.toString(), mainStage);
            System.exit(0);
        }
    }

    /**
     * Changes the center view to the compressionView when the compression button is pressed.
     */
    public void compressionPressed() { borderpane.setCenter(compressionView); }

    /**
     * Changes the center view to the decompressionView when the decompression button is pressed.
     */
    public void decompressionPressed() {
        borderpane.setCenter(decompressionView);
    }

    /**
     * Changes the center view to the statisticView when the statistic button is pressed.
     */
    public void statisticPressed() {
        borderpane.setCenter(statisticView);
    }

    /**
     * Changes the center view to the historyView when the history button is pressed.
     */
    public void historyPressed() { borderpane.setCenter(historyView); }

    /**
     * Changes the center view to the comparisionView when the comparision button is pressed.
     */
    public void comparisionPressed() {
        borderpane.setCenter(comparisionView);
    }

    /**
     * Set the default behaviour when the exit button is pressed:
     *      -Stores the histories and the global statistics
     *      -Closes the program
     * @throws PresentationLayerException
     */
    public void exitPressed() throws PresentationLayerException {
        PresentationCtrl.getInstance().closeAndSave();
        System.exit(0);
    }
}
