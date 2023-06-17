package db;

import java.sql.*;

public class database {
    public static void main(String[] args) {
        String dbUrl = "jdbc:ucanaccess://C:\\Users\\Cyberpower\\Downloads\\Zlagoda.accdb";

        try {
            Connection connection = DriverManager.getConnection(dbUrl);
            System.out.println("connected!");

            String query1 = "SELECT * FROM LoginNPasswords";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query1);

            while(result.next()){
                String employee_id = result.getString("id_employee");
                String password = result.getString("password");

                System.out.println(employee_id+", "+password);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
