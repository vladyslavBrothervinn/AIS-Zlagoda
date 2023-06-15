package instruments;

import db.DatabaseManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;

public class TableManager<T extends Table> {
  //  AsyncRenew asyncRenew;
    private ObservableList<T> data;
  //  private FilteredList<T> filteredData;
    private String tableName;
    public DatabaseManager databaseManager;
    private TableView<T> tableView;
    private ResultSet resultSet;
    private String[] keyColumnsNames;
    private String[] nonKeyColumnsNames;
    private LinkedList<TableFilter> keyFilters;
    private LinkedList<TableFilter> substringFilters;
    private LinkedList<TableFilter> dateFilters;

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
        keyFilters = new LinkedList<>();
        substringFilters = new LinkedList<>();
        dateFilters = new LinkedList<>();
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
        nonKeyColumnsNames = new String[resultSet.getMetaData().getColumnCount()-1];
        for (int i = 0; i < resultSet.getMetaData().getColumnCount()-1; i++){
            nonKeyColumnsNames[i] = resultSet.getMetaData().getColumnName(i+2);
        }
        /*
        asyncRenew = new AsyncRenew();
        asyncRenew.setDaemon(true);
        asyncRenew.start();
         */
    }

    /**Gets all data from database again and renews data in tableView
     *
     * @throws SQLException
     */
    public void renewTable() throws SQLException {
        int row = tableView.getSelectionModel().getSelectedIndex();
        data.clear();
        if(keyFilters.isEmpty()&&substringFilters.isEmpty()&&dateFilters.isEmpty())
        resultSet = databaseManager.selectRecords(tableName);
        else {
            String[] keyFilterColumnNames = new String[keyFilters.size()];
            String[] keyFilterValues = new String[keyFilters.size()];
            String[] substringFilterColumnNames = new String[substringFilters.size()];
            String[] substringFilterValues = new String[substringFilters.size()];
            String[] dateFilterColumnNames = new String[dateFilters.size()];
            Date[] dateFilterValues = new Date[dateFilters.size()*2];

            for(int i = 0; i < keyFilters.size(); i++){
                keyFilterColumnNames[i] = keyFilters.get(i).fieldName;
                keyFilterValues[i] = keyFilters.get(i).fieldValues[0];
            }
            for (int i = 0; i < substringFilters.size(); i++){
                substringFilterColumnNames[i] = substringFilters.get(i).fieldName;
                substringFilterValues[i] = substringFilters.get(i).fieldValues[0];
            }
            for(int i = 0; i < dateFilters.size(); i++){
                dateFilterColumnNames[i] = dateFilters.get(i).fieldName;
                dateFilterValues[i*2] = Date.valueOf(dateFilters.get(i).fieldValues[0]);
                dateFilterValues[i*2+1] = Date.valueOf(dateFilters.get(i).fieldValues[1]);
            }
            resultSet = databaseManager.selectSpecifiedRecords(tableName, keyFilterColumnNames, keyFilterValues, substringFilterColumnNames,
                    substringFilterValues, dateFilterColumnNames, dateFilterValues, null);
        }
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
     */
    public void updateRow(String[] keyValues, T newRowValue) throws SQLException {
        String[] columnsValues = newRowValue.getFieldsValuesAsStringArray();
        String[] nonKeyColumnsValues = new String[columnsValues.length-keyValues.length];
        System.arraycopy(columnsValues, keyValues.length, nonKeyColumnsValues, 0, nonKeyColumnsValues.length);
        //Check for promotional
        if(Objects.equals(tableName, "Store_Product")){
            Store_Product store_product = (Store_Product) newRowValue;
            if(store_product.getPromotionalProduct()){
                if(!Objects.equals(store_product.getUpcProm(), "")) throw new IllegalArgumentException("Promotional product cannot have promotional UPC!");
                double sellingPrice;
                try {
                    String sql = "SELECT selling_price FROM Store_Product WHERE id_product = " + nonKeyColumnsValues[1] + " AND promotional_product = false";
                    System.out.println(sql);
                    ResultSet resultSet = databaseManager.statement.executeQuery(sql);
                    resultSet.next();
                    sellingPrice = resultSet.getDouble(1)*0.8;
                }
                catch (Exception e){
                    e.printStackTrace();
                    sellingPrice = store_product.getSellingPrice();
                }
                nonKeyColumnsValues[2] = String.valueOf(sellingPrice);
            }
            else databaseManager.statement.executeUpdate("UPDATE Store_Product SET selling_price = "+Double.parseDouble(nonKeyColumnsValues[2])*0.8+" WHERE id_product = " + nonKeyColumnsValues[1]);
        }
        databaseManager.updateRecords(tableName, keyColumnsNames, keyValues, nonKeyColumnsNames, nonKeyColumnsValues);
        renewTable();
    }

    /** Deletes one record
     *
     * @param keyValues values of primary key(s)
     */
    public void deleteRow(String[] keyValues) throws SQLException {
        databaseManager.deleteRecords(tableName, keyColumnsNames, keyValues);
        renewTable();
    }

    /** Inserts one record
     *
     * @param newRowValue object which will be a new value of the row
     */
    public void insertRow(T newRowValue) throws SQLException {
        String[] columnValues = newRowValue.getFieldsValuesAsStringArray();
        if(Objects.equals(tableName, "Store_Product")){
            Store_Product store_product = (Store_Product) newRowValue;
            ResultSet resultSet = databaseManager.statement.executeQuery("SELECT promotional_product, UPC FROM Store_Product WHERE id_product = " + store_product.getIdProduct());
           Boolean b = null;
            if (resultSet.next())
               b = resultSet.getBoolean(1);
            if(resultSet.next()||(b!=null&&b==store_product.getPromotionalProduct()))
                throw new IllegalArgumentException(store_product.getPromotionalProduct()?"This product already has promotional product!":
                        "This product already exists in the store!");
            if(store_product.getPromotionalProduct()) {
                if (!Objects.equals(store_product.getUpcProm(), ""))
                    throw new IllegalArgumentException("Promotional product cannot have promotional UPC!");
                databaseManager.statement.executeUpdate("UPDATE Store_Product SET UPC_prom = "+store_product.getUpcProm()+" WHERE id_product = " + store_product.getUpcProm()+" AND promotional_product = false");
            }
            else if(!Objects.equals(store_product.getUpcProm(), "")){
                databaseManager.statement.executeUpdate("UPDATE Store_Product SET selling_price = "+store_product.getSellingPrice()*0.8+
                        " WHERE UPC IN (SELECT UPC_prom FROM Store_Product WHERE UPC = " + store_product.getUpcProm() +")");
            }
        }
        databaseManager.insertRecord(tableName, columnValues);
        renewTable();
    }

    /**
     * Class which represents simple filter: key filter means that field can contain only this value,
     *  substring filter means that the field must have this substring(only for String type)
     *  date filter means that the field must be between two dates in array(only for dates)
     */
    public class TableFilter{
        public String fieldName;
        public String[] fieldValues;
        public TableFilter(String fieldName, String[] fieldValues){
            this.fieldName = fieldName;
            this.fieldValues = fieldValues;
        }
    }
    public void removeAllFilters() throws SQLException {
        keyFilters.clear();
        substringFilters.clear();
        dateFilters.clear();
        renewTable();
    }
    //If type is String or date, write in this way: 'someString'
    public void addKeyFilter(String fieldName, String fieldValue) throws SQLException {
        TableFilter tableFilter = new TableFilter(fieldName, new String[]{fieldValue});
        if(!keyFilters.contains(tableFilter)) keyFilters.add(tableFilter);
        renewTable();
    }
    public void addSubstringFilter(String fieldName, String substring) throws SQLException {
        TableFilter tableFilter = new TableFilter(fieldName, new String[]{substring});
        if(!substringFilters.contains(tableFilter)) substringFilters.add(tableFilter);
        renewTable();
    }
    public void addDateFilter(String fieldName, String[] dates) throws SQLException {
        TableFilter tableFilter = new TableFilter(fieldName, dates);
        if(!dateFilters.contains(tableFilter)) dateFilters.add(tableFilter);
        renewTable();
    }
    public void removeKeyFilter(TableFilter tableFilter) throws SQLException {
        keyFilters.remove(tableFilter);
        renewTable();
    }
    public void removeSubstringFilter(TableFilter tableFilter) throws SQLException {
        substringFilters.remove(tableFilter);
        renewTable();
    }
    public void removeDateFilter(TableFilter tableFilter) throws SQLException {
        dateFilters.remove(tableFilter);
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
