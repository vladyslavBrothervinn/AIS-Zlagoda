package db;

import java.sql.*;

public class database {
    public static void main(String[] args) {
        String dbUrl = "jdbc:ucanaccess://C:\\Users\\Cyberpower\\Desktop\\Юниверсити\\ukma\\cs-2\\бдіс\\term2\\кр\\2\\db_test.accdb";

        try {
            Connection connection = DriverManager.getConnection(dbUrl);
            System.out.println("connected!");

            String query1 = "SELECT * FROM Prj";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query1);

            while(result.next()){
                int id_p = result.getInt("id_p");
                String p_name = result.getString("p_name");
                String budget = result.getString("budget");
                String start_date = result.getString("start_date");

                System.out.println(id_p+", "+p_name+", "+budget+", "+start_date);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
