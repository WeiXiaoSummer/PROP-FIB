package Presentation;

import Commons.PresentationLayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PROP extends Application {
    private Stage primary;

    @Override
    public void start(Stage primaryStage) throws Exception {
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

    private static void showErrorDialog(Throwable e) {
        System.out.println(e.getMessage());
    }
}
