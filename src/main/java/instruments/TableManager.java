package instruments;

import db.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableManager<T> {
    private ObservableList<T> data;

    /** Table manager, connects data shown in tableView to database
     *
     * @param databaseManager database manager, simply "DatabaseManager.getDatabaseManager()"
     * @param tableView tableView which you want to connect to database. Make sure that
     *                  there are no columns in, or they have correct types and order
     * @param tableName name of table in database to which you want to connect tableView
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public TableManager(DatabaseManager databaseManager, TableView<T> tableView, String tableName) throws SQLException, ClassNotFoundException {
        ResultSet rs = databaseManager.selectRecords(tableName);
        data = FXCollections.observableArrayList();
        Class<?> tClass = Class.forName("models."+tableName);
        if(tableView.getColumns().isEmpty())
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new PropertyValueFactory<>(
                    tClass.getDeclaredFields()[i].getName()
                    )
            );
            tableView.getColumns().add(col);
        }
                switch (tableName){
                    case "Category":
                        while (rs.next()) data.add((T) new Category(rs.getInt(1), rs.getString(2)));
                        break;
                    case "Check":
                        while (rs.next()) data.add((T) new Check(rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getDate(4), rs.getDouble(5),
                                rs.getDouble(6)));
                        break;
                    case "Customer_Card":
                        while (rs.next()) data.add((T) new Customer_Card(rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getString(4),
                                rs.getString(5), rs.getString(6),
                                rs.getString(7), rs.getString(8),
                                rs.getInt(9)));
                        break;
                    case "Employee":
                        while (rs.next()) data.add((T) new Employee(rs.getString(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getDate(7),rs.getDate(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
                        break;
                    case "Product":
                        while (rs.next()) data.add((T) new Product(rs.getInt(1), rs.getInt(2),
                                rs.getString(3), rs.getString(4)));
                        break;
                    case "Sale":
                        while (rs.next()) data.add((T) new Sale(rs.getString(1), rs.getString(2), rs.getInt(3),
                                rs.getDouble(4)));
                        break;
                    case "Store_Product":
                        while (rs.next()) data.add((T) new Store_Product(rs.getString(1), rs.getString(2), rs.getInt(3),
                                rs.getDouble(4), rs.getInt(5), rs.getBoolean(6)));
                        break;
                    default: break;
        }
        tableView.setItems(data);
    }
}
