package instruments;

import db.DatabaseManager;
import models.Store_Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class StoreProductManager {
    public StoreProductManager(){
    }

    public int getAmountOfProduct(String upc) throws SQLException {
        try {
            ResultSet resultSet = DatabaseManager.getDatabaseManager().statement.executeQuery("SELECT products_number FROM Store_Product WHERE UPC = "+upc);
            if (resultSet.next())
                return resultSet.getInt(1);
            return -1;
        }
        catch (Exception e) {
            return -1;
        }
    }

    public void addAmountOfProduct(String upc, int amount) throws SQLException, NoSuchElementException {
        if(getAmountOfProduct(upc)==-1) throw new NoSuchElementException("This store product does not exist!");
        DatabaseManager.getDatabaseManager().statement.executeUpdate("UPDATE Store_Product SET products_number = products_number + "+amount+
                " WHERE UPC = " + upc);
    }

    public void addAmountOfProduct(String upc, int amount, double newPrice) throws SQLException, NoSuchElementException {
        if(getAmountOfProduct(upc)==-1) throw new NoSuchElementException("This store product does not exist!");
        DatabaseManager.getDatabaseManager().statement.executeUpdate("UPDATE Store_Product SET products_number = products_number + "+amount+
                ", selling_price = "+newPrice+" WHERE UPC = " + upc);
        DatabaseManager.getDatabaseManager().statement.executeUpdate("UPDATE Store_Product SET selling_price = "+newPrice*0.8+
                " WHERE UPC IN (SELECT UPC_prom FROM Store_Product WHERE UPC = " + upc +")");
    }

    public void removeAmountOfProduct(String upc, int amount) throws Exception {
        int currentAmount = getAmountOfProduct(upc);
        if(currentAmount==-1) throw new NoSuchElementException("This store product does not exist!");
        int newAmount = currentAmount - amount;
        if(newAmount<0) throw new Exception("Not enough product in store!");
        String sql = "UPDATE Store_Product SET products_number = "+newAmount+
                " WHERE UPC = " + upc;
        DatabaseManager.getDatabaseManager().statement.executeUpdate(sql);
    }

   // public void convertAmountOfProductToPromotional(String upc, int amount){
  //
   // }
}
