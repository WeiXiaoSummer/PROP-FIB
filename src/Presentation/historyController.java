package Presentation;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This is a controller Class which controls the historyView
 */
public class historyController implements Initializable {
    private @FXML TableView tableView;
    private ArrayList<String> columnNames;

    /**
     * Initializes the historyView when it's loaded.
     *      -Load the column names to be displayed
     *      -Load the histories
     * @param location default location
     * @param resources default resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNames = PresentationCtrl.getInstance().getHistoryColumnNames();
        ObservableList<String[]> data = PresentationCtrl.getInstance().getHistories();
        for (int i = 0; i < 5; ++i) {
            final int index = i;
            TableColumn<String[], String> tblCol = new TableColumn<String[], String>(columnNames.get(i));
            tblCol.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue()[index]));
            tableView.getColumns().add(tblCol);
        }
        setDoubleClickOnTable();
        tableView.setItems(data);
    }

    /**
     * Set the default behaviour when the refresh button is pressed:
     *      -reload histories
     */
    public void refreshPressed() {
        ObservableList<String[]> data = PresentationCtrl.getInstance().getHistories();
        tableView.setItems(data);
    }

    /**
     * Set the default behaviour when the clear button is pressed:
     *      -clear all histories
     */
    public void clearPressed() {
        PresentationCtrl.getInstance().clearHistory();
        refreshPressed();
    }

    /**
     * Set the default behaviour when double clicks on a row of the tableView
     *      -Shows a dialog which contains detailed information of the selected local history
     */
    public void setDoubleClickOnTable(){
        tableView.setRowFactory(tableView-> {
            TableRow<String[]> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                displayHistory(event, row);
            });
            return row;
        });
    }

    /**
     * Checks the mouseEvent, if it's a double click then show the selected row
     * @param event mouseEvent
     * @param row selected row
     */
    public void displayHistory(MouseEvent event, TableRow<String[]> row) {
        //Checks if it's a double click
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2 && (!row.isEmpty())) {

            //Paint and show the dialog
            String[] lh = row.getItem();
            GridPane gridPane = new GridPane();
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.getColumnConstraints().add(new ColumnConstraints(152));
            gridPane.getColumnConstraints().add(new ColumnConstraints(313));
            for (int i = 0; i < columnNames.size(); i++) {
                gridPane.getRowConstraints().add(new RowConstraints(30));
                TextField colContent = new TextField(lh[i]);
                Text colname = new Text(columnNames.get(i));
                colContent.setEditable(false);
                GridPane.setMargin(colContent, new Insets(0, 20, 0, 20));
                GridPane.setHalignment(colname, HPos.CENTER);
                gridPane.add(colname, 0, i);
                gridPane.add(colContent, 1, i);
            }
            gridPane.getRowConstraints().add(new RowConstraints(30));
            Button accept = new Button("ACCEPT");
            accept.setPrefSize(70, 30);
            GridPane.setHalignment(accept, HPos.RIGHT);
            gridPane.add(accept, 1, columnNames.size());
            Scene scene = new Scene(gridPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            accept.setOnAction(actionEvent -> stage.close());
            stage.setTitle("Show local history");
            stage.setResizable(false);
            stage.show();
        }
    }
}
