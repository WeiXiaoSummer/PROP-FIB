package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main runnable Class
 */
public class PROP extends Application {
    private Stage primary;

    /**
     * Starts the program.
     * @param primaryStage the primary stage where the program is showed.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            //Set the default behaviour when a exception throws:
            //      -Display a message box showing the error message
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> {
                PresentationCtrl.getInstance().showNotification("Error", "Error", "An internal error has occurred:", e.getMessage(), primary);
            }));
            Thread.currentThread().setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> {
                PresentationCtrl.getInstance().showNotification("Error", "Error", null, e.getMessage(), primary);
            }));
            PresentationCtrl.getInstance().initializeDomainCtrl();
            primary = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("mainView/mainView.fxml"));
            primary.setTitle("PROP");
            primary.setScene(new Scene(root));
            primary.setResizable(false);
            primaryStage.sizeToScene();
            primary.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Stage stage = new Stage();
            stage.setScene(new Scene(new Pane()));
            stage.show();
            PresentationCtrl.getInstance().showNotification("Error", "Error", "Cannot load stage",
                    "An error has occured while trying to load the stage, program aborted. \n\n" +
                            "See below\n\n"+e.toString(), stage);
        }
    }

    /**
     * Make program stores important information before it's closed.
     * @throws PresentationLayerException
     */
    @Override
    public void stop() throws PresentationLayerException {
        PresentationCtrl.getInstance().closeAndSave();
    }

    public static void main( String[] args )
    {
        launch(args);
    }

}
