package instruments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.*;

public class SQLBasedTable {
    TableView tableView;
    public SQLBasedTable(TableView tableView){
        this.tableView = tableView;
    }

    public void runSQL(String sql, String... parameters) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:ucanaccess://src/main/resources/Zlagoda.accdb;COLUMNORDER=DISPLAY");
        PreparedStatement statement = connection.prepareStatement(sql);

        for(int i = 0; i < parameters.length; i++){
            statement.setString(i+1, parameters[i]);
        }

        tableView.getColumns().clear();

        ResultSet rs = statement.executeQuery();

        for(int i=0; i<rs.getMetaData().getColumnCount(); i++){
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                    param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            tableView.getColumns().addAll(col);
        }

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        while (rs.next()){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for(int i = 0; i<rs.getMetaData().getColumnCount(); i++){
                observableList.add(rs.getObject(i+1).toString());
            }
            data.add(observableList);
        }
        tableView.setItems(data);
        statement.close();
        connection.close();
    }
}
