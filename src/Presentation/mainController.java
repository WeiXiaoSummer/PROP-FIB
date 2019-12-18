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

public class mainController implements Initializable {
    private @FXML BorderPane borderpane;
    private @FXML StackPane compressionView;
    private @FXML StackPane decompressionView;
    private @FXML Pane statisticView;
    private @FXML Pane historyView;
    private @FXML StackPane comparisionView;
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

    public void compressionPressed() { borderpane.setCenter(compressionView); }

    public void decompressionPressed() {
        borderpane.setCenter(decompressionView);
    }

    public void statisticPressed() {
        borderpane.setCenter(statisticView);
    }

    public void historyPressed() { borderpane.setCenter(historyView); }

    public void comparisionPressed() {
        borderpane.setCenter(comparisionView);
    }

    public void exitPressed() throws PresentationLayerException {
        PresentationCtrl.getInstance().closeAndSave();
        System.exit(0);
    }
}
