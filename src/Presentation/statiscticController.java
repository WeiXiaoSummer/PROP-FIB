package Presentation;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class statiscticController implements Initializable {

    private @FXML TableView tableView;
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


}
