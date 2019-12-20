package Presentation;

import Commons.PresentationLayerException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is a controller class which controls the mainView
 */
public class mainController implements Initializable {
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
            compressionView = FXMLLoader.load(getClass().getResource("fxml/compression.fxml"));
            decompressionView = FXMLLoader.load(getClass().getResource("fxml/decompression.fxml"));
            statisticView = FXMLLoader.load(getClass().getResource("fxml/statistic.fxml"));
            historyView = FXMLLoader.load(getClass().getResource("fxml/history.fxml"));
            comparisionView = FXMLLoader.load(getClass().getResource("fxml/comparision.fxml"));
            borderpane.setCenter(compressionView);
        } catch (Exception e) {
            e.printStackTrace();
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
