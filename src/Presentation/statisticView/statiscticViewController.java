package Presentation.statisticView;

import Presentation.PresentationCtrl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class statiscticViewController implements Initializable {

    private @FXML TableView tableView;

    /**
     * Initializes the statisticView when it's loaded.
     *      -Load the column names to be displayed
     *      -Load the statistics
     * @param location default location
     * @param resources default resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        String[] columnNames = PresentationCtrl.getInstance().getStatisticColumnNames();
        ObservableList<String[]> data = PresentationCtrl.getInstance().getStatistics();
        for (int i = 0; i < columnNames.length; ++i) {
            final int index = i;
            TableColumn<String[], String> tblCol = new TableColumn<>(columnNames[i]);
            tblCol.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue()[index]));
            tableView.getColumns().add(tblCol);
        }
        tableView.setItems(data);
    }

    /**
     * Set the default behaviour when the refresh button is pressed:
     *      -reload statistics
     */
    public void refreshPressed() {
        ObservableList<String[]> data = PresentationCtrl.getInstance().getStatistics();
        tableView.setItems(data);
    }

}
