package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PROP extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));//getClass().getClassLoader().getResource() 和 getClass().getResource()的区别，在不在根目录下
        primaryStage.setTitle("PROP");
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);//窗体缩放（默认为true）
        primaryStage.show();
    }
    public static void main( String[] args )
    {
        launch(args);
    }
}
