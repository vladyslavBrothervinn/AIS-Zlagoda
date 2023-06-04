package instruments;

import db.DatabaseManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class TableManager<T extends Table> {
  //  AsyncRenew asyncRenew;
    private ObservableList<T> data;
  //  private FilteredList<T> filteredData;
    private String tableName;
    private DatabaseManager databaseManager;
    private TableView<T> tableView;
    private ResultSet resultSet;
    private String[] keyColumnsNames;
    private String[] columnsNames;

    /** Table manager, connects data shown in tableView to database
     *
     * @param databaseManager database manager, for default set as null
     * @param tableView tableView which you want to connect to database. Make sure that
     *                  there are no columns in, or they have correct types and order
     * @param tableName name of table in database to which you want to connect tableView
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public TableManager(DatabaseManager databaseManager, TableView<T> tableView, String tableName) throws SQLException, ClassNotFoundException {
        this.tableView = tableView;
        if(databaseManager==null) this.databaseManager = DatabaseManager.getDatabaseManager();
        else this.databaseManager = databaseManager;
        resultSet = databaseManager.selectRecords(tableName);
        data = FXCollections.observableArrayList();

        Class<?> tClass = Class.forName("models."+tableName);
        if(tableView.getColumns().isEmpty())
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new PropertyValueFactory<>(
                    tClass.getDeclaredFields()[i].getName()
                    )
            );
            tableView.getColumns().add(col);
        }
        this.tableName = tableName;
        renewTable();
        tableView.setItems(data);
        keyColumnsNames = new String[Objects.equals(tableName, "Sale") ?2:1];
        for (int i = 0; i < keyColumnsNames.length; i++){
            keyColumnsNames[i] = resultSet.getMetaData().getColumnName(i+1);
        }
        columnsNames = new String[resultSet.getMetaData().getColumnCount()];
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++){
            columnsNames[i] = resultSet.getMetaData().getColumnName(i+1);
        }
        /*
        asyncRenew = new AsyncRenew();
        asyncRenew.setDaemon(true);
        asyncRenew.start();
         */
    }

    /**Gets all data from database again and renews data in tableView
     *
     * @throws SQLException good luck with fixing :)
     */
    public void renewTable() throws SQLException {
        int row = tableView.getSelectionModel().getSelectedIndex();
        data.clear();
        resultSet = databaseManager.selectRecords(tableName);
        switch (tableName){
            case "Category":
                while (resultSet.next()) data.add((T) new Category(resultSet.getInt(1), resultSet.getString(2)));
                break;
            case "Check":
                while (resultSet.next()) data.add((T) new Check(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getDate(4), resultSet.getDouble(5),
                        resultSet.getDouble(6)));
                break;
            case "Customer_Card":
                while (resultSet.next()) data.add((T) new Customer_Card(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8),
                        resultSet.getInt(9)));
                break;
            case "Employee":
                while (resultSet.next()) data.add((T) new Employee(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6), resultSet.getDate(7),resultSet.getDate(8),
                        resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12)));
                break;
            case "Product":
                while (resultSet.next()) data.add((T) new Product(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4)));
                break;
            case "Sale":
                while (resultSet.next()) data.add((T) new Sale(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
                        resultSet.getDouble(4)));
                break;
            case "Store_Product":
                while (resultSet.next())
                    data.add((T) new Store_Product(resultSet.getString(1),
                            resultSet.getString(2), resultSet.getInt(3),
                            resultSet.getDouble(4), resultSet.getInt(5), resultSet.getBoolean(6)));
                break;
            default: break;
        }
        tableView.sort();
        Platform.runLater(() -> {
            tableView.requestFocus();
            tableView.getSelectionModel().select(row);
            tableView.getFocusModel().focus(row);
        });
    }

    /** Updates one record
     *
     * @param keyValues values of primary key(s)
     * @param newRowValue object which will be a new value of the row(make sure that primary keys are same as in updated row)
     * @throws SQLException good luck with fixing :)
     */
    public void updateRow(String[] keyValues, T newRowValue) throws SQLException {
        String[] columnsValues = newRowValue.getFieldsValuesAsStringArray();
        databaseManager.updateRecords(tableName, keyColumnsNames, keyValues, columnsNames, columnsValues);
        renewTable();
    }

    /** Deletes one record
     *
     * @param keyValues values of primary key(s)
     * @throws SQLException good luck with fixing :)
     */
    public void deleteRow(String[] keyValues) throws SQLException {
        databaseManager.deleteRecords(tableName, keyColumnsNames, keyValues);
        renewTable();
    }

    /** Inserts one record
     *
     * @param newRowValue object which will be a new value of the row
     * @throws SQLException good luck with fixing :)
     */
    public void insertRow(T newRowValue) throws SQLException {
        String[] columnValues = newRowValue.getFieldsValuesAsStringArray();
        databaseManager.insertRecord(tableName, columnValues);
        renewTable();
    }

/*
    private class AsyncRenew extends Thread{
        public void run(){
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(true){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    renewTable();
                    System.out.println("TableManager: AsyncRenew has renewed table");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

 */
}
