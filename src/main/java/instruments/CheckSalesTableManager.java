package instruments;

import db.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckSalesTableManager {
    TableView tableView;
    String checkNumber;
    private ObservableList<ObservableList> data;
    public CheckSalesTableManager(TableView tableView, String checkNumber) throws SQLException {
        this.tableView = tableView;
        this.checkNumber = checkNumber;
        tableView.getColumns().clear();
        ResultSet rs = DatabaseManager.getDatabaseManager().statement.executeQuery("SELECT Product.id_product, product_name, product_number, Sale.selling_price "+
                "FROM ((Sale INNER JOIN Store_Product ON Sale.UPC = Store_Product.UPC) "+
                "INNER JOIN Product ON Product.id_product = Store_Product.id_product)");
        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                    param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            tableView.getColumns().addAll(col);
        }
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        renewTable();
    }

    public void renewTable() throws SQLException {
        String sql = "SELECT Product.id_product, product_name, product_number, Sale.selling_price "+
        "FROM ((Sale INNER JOIN Store_Product ON Sale.UPC = Store_Product.UPC) "+
        "INNER JOIN Product ON Product.id_product = Store_Product.id_product)" +
                "WHERE Sale.check_number = "+ checkNumber;
        ResultSet rs = DatabaseManager.getDatabaseManager().statement.executeQuery(sql);
        data.clear();
        while(rs.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                row.add(rs.getString(i));
            }
            data.add(row);

        }
    }
}
