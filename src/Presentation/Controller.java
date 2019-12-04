package Presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button btnCompressió;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnDescompressió;

    @FXML
    private Button btnEstadistiques;

    @FXML
    private Button btnHistoria;

    @FXML
    private Button btnComparació;

    @FXML
    private Button btnVisualització;

    @FXML
    private Button selectFile;

    @FXML
    private GridPane pnCompressió;

    @FXML
    private GridPane pnDescompressió;

    @FXML
    private GridPane pnHistoria;

    @FXML
    private GridPane pnEstadistiques;

    @FXML
    private GridPane pnComparació;

    @FXML
    private GridPane pnVisualització;

    @FXML
    private Label titile;

    @FXML
    private ListView<String> listViewLeft;

    @FXML
    private ListView<String> listViewRight;

    @FXML
    private ComboBox<String> choice;

    @FXML
    private ComboBox<String> choice2;

    @FXML
    private TextField filePath;

    @FXML
    private TextField directoryPath;

    @FXML
    private TextField comparacioFilePath;

    @FXML
    private TextField descompFilePath;

    @FXML
    private TextField visualitzacióFilePath;

    private Window primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> strList = FXCollections.observableArrayList("numCompression : ",
        "numDecompression : ","totalCompressedData : ", "totalDecompressedData : ", "totalCompressionTime : ",
        "totalDecompressionTime : ", "averageCompressionRatio : ");
        listViewLeft.setItems(strList);

        ObservableList<String> strList2 = FXCollections.observableArrayList("0","0","0","0","0","0","0");
        listViewRight.setItems(strList2);

        choice.setItems(FXCollections.observableArrayList("LZSS","LZ78","JPEG"));
        choice.setPromptText("Select algorithm");

        choice2.setItems(FXCollections.observableArrayList("LZSS","LZ78","JPEG"));
        choice2.setPromptText("Select algorithm");
    }

    @FXML
    public void handleClicks(javafx.event.ActionEvent event) {
        if (event.getSource() == btnCompressió) {
            titile.setText("COMPRESSION");
            pnCompressió.toFront();
        }
        else if (event.getSource() == btnDescompressió){
            titile.setText("DESCOMPRESSION");
            pnDescompressió.toFront();
        }
        else if (event.getSource() == btnEstadistiques){
            pnEstadistiques.toFront();
            titile.setText("STATISTIC");
        }
        else if (event.getSource() == btnHistoria){
            pnHistoria.toFront();
            titile.setText("HISTORY");
        }
        else if (event.getSource() == btnComparació){
            pnComparació.toFront();
            titile.setText("COMPARISION");
        }
        else if (event.getSource() == btnVisualització){
            pnVisualització.toFront();
            titile.setText("VISUALITZATION");
        }
        else if (event.getSource() == btnSalir){
            btnSalir.setCancelButton(true);
        }
    }
    public void selectCompFile(javafx.event.ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt")
            , new FileChooser.ExtensionFilter("Image Files", "*.ppm"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                String path = file.getPath();
                filePath.setText(path);
            }
    }

    public void selectCompDirectory(javafx.event.ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(primaryStage);
        if (file != null) {
            String path = file.getPath();
            filePath.setText(path);
        }
    }

    public void selectSaveDirectory(javafx.event.ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(primaryStage);
        if (file != null) {
            String path = file.getPath();
            directoryPath.setText(path);
        }
    }

    public void selectDescompFile(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("LZSS Files", "*.lzss")
                , new FileChooser.ExtensionFilter("LZ78 Files", "*.lz78")
                , new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            String path = file.getPath();
            descompFilePath.setText(path);
        }
    }

    public void selectComparacióFile(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt")
                , new FileChooser.ExtensionFilter("Image Files", "*.ppm"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            String path = file.getPath();
            comparacioFilePath.setText(path);
        }
    }

    public void selectVisualizacióFile(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Folder Compressed", "*.prop"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            String path = file.getPath();
            visualitzacióFilePath.setText(path);
        }
    }

}
