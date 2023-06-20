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
import models.Sale;
import models.Store_Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckSalesTableManager {
    public StoreProductManager storeProductManager = new StoreProductManager();
    TableView tableView;
    private ObservableList<ObservableList<String>> data;
    public CheckSalesTableManager(TableView tableView) throws SQLException {
        this.tableView = tableView;
        tableView.getColumns().clear();
        ResultSet rs = DatabaseManager.getDatabaseManager().statement.executeQuery("SELECT UPC, product_name, product_number, Sale.selling_price AS price"+
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
    }

    public void addToTable(String upc, String product_name, Integer product_number, Double selling_price, TableManager storeTable) throws Exception {
        selling_price = selling_price*product_number;
        System.out.println("Removing...");
        storeProductManager.removeAmountOfProduct(upc, product_number);
        if(storeTable!=null){
            storeTable.renewTable();
        }
        ObservableList<String> added = FXCollections.observableArrayList();
        added.add(upc);
        added.add(product_name);
        added.add(product_number.toString());
        added.add(selling_price.toString());
        data.add(added);

    }
    public void removeFromTable(int rowNumber, TableManager tableManager) throws SQLException {
        storeProductManager.addAmountOfProduct(data.get(rowNumber).get(0), Integer.parseInt(data.get(rowNumber).get(2)));
        data.remove(rowNumber);
        if(tableManager!=null)  tableManager.renewTable();
    }
    public Double getSum(){
        Double sum = 0.0;
        for (int i = 0; i < data.size(); i++){
            sum+=Double.parseDouble(data.get(i).get(3));
        }
        return  sum;
    }
    public void addAllToCheck(String checkId) throws SQLException {
        for(int i = 0; i < data.size(); i++){
            Sale sale = new Sale(data.get(i).get(0), checkId, Integer.parseInt(data.get(i).get(2)), Double.parseDouble(data.get(i).get(3)));
            DatabaseManager.getDatabaseManager().insertRecord("Sale", new String[]{"'"+sale.getUpc()+"'",
                    "'"+sale.getCheckNumber()+"'", String.valueOf(sale.getProductNumber()), String.valueOf(sale.getSellingPrice())});
        }
    }
    public void cancel() throws SQLException {
        while (data.size()>0){
            removeFromTable(0, null);
        }
    }


    public void renewTable(String checkNumber) throws SQLException {
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
