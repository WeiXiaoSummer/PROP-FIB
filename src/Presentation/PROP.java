package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void start(Stage primaryStage) throws Exception {

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
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        primary.setTitle("PROP");
        primary.setScene(new Scene(root));
        primary.setResizable(false);
        primaryStage.sizeToScene();
        primary.show();

    }
    @Override
    public void stop() throws PresentationLayerException {
        PresentationCtrl.getInstance().closeAndSave();
    }

    public static void main( String[] args )
    {
        launch(args);
    }

}
