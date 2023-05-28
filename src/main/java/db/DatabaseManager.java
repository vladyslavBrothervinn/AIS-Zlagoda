package db;
import java.sql.*;

public class DatabaseManager {
    Connection connection;
    Statement statement;
    private static DatabaseManager databaseManager;
    public static DatabaseManager getDatabaseManager() throws SQLException {
        if(databaseManager==null) databaseManager = new DatabaseManager("jdbc:ucanaccess://src/main/resources/Zlagoda.accdb");
        return databaseManager;
    }
    public DatabaseManager(String sqlAddress) throws SQLException {
        connection = DriverManager.getConnection(sqlAddress);
        statement = connection.createStatement();
    }

    /** Inserts one record into table, values of every column should be given
     *
     * @param table table name
     * @param values values of record as String[], for example: {"9281", "'John'", "'Smith'"}
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public void insertRecord(String table, String[] values) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder("INSERT INTO " + table + " VALUES (");
        for(int i = 0; i < values.length; i++){
            sqlQuery.append(values[i]).append(values.length == i + 1 ? ")" : ", ");
        }
        statement.executeUpdate(sqlQuery.toString());
        //System.out.println(sqlQuery);
    }

    /** Inserts one record into table, but data is specified only for chosen columns (can be used, for example, when you have autoincrement key)
     *
     * @param table table name
     * @param columnNames names of columns for which you give the values
     * @param values values of your columns as String[], for example: {"9281", "'John'", "'Smith'"}, same length as "columnNames"
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public void insertRecordWithSpecifiedColumns(String table, String[] columnNames, String[] values) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder("INSERT INTO " + table + " (");
        for (int i = 0; i < columnNames.length; i++){
            sqlQuery.append(columnNames[i]).append(columnNames.length == i + 1 ? ")" : ", ");
        }
        sqlQuery.append(" VALUES (");
        for(int i = 0; i < values.length; i++){
            sqlQuery.append(values[i]).append(values.length == i + 1 ? ")" : ", ");
        }
        statement.executeUpdate(sqlQuery.toString());
        //System.out.println(sqlQuery);
    }

    /** Updates records specified by keys
     *
     * @param table name of table
     * @param keyColumnsNames names of columns by which you find a record, in general - names of primary key columns
     * @param keyValues values of columns by which you find a record, same length as "keyColumnNames"
     * @param columns names of columns values of which you want to update
     * @param values values of columns which you set, same length as "columns"
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public void updateRecords(String table, String[] keyColumnsNames,
                              String[] keyValues, String[] columns, String[] values) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder("UPDATE " + table + " SET ");
        for (int i = 0; i < columns.length; i++){
            sqlQuery.append(columns[i]).append(" = ").append(values[i]).append(columns.length==i+1?"":", ");
        }
        sqlQuery.append(" WHERE");
        for(int i = 0; i < keyColumnsNames.length; i++){
            sqlQuery.append(" ").append(keyColumnsNames[i]).append(" = ").append(keyValues[i]).append(keyColumnsNames.length == i + 1 ? "" : " AND");
        }
        statement.executeUpdate(sqlQuery.toString());
        //System.out.println(sqlQuery);
        //DatabaseMetaData meta = conn.getMetaData();
        //getPrimaryKeys()
    }

    /** Deletes records specified by keys
     *
     * @param table name of table
     * @param keyColumnsNames names of columns by which you find a record, in general - names of primary key columns
     * @param keyValues values of columns by which you find a record, same length as "keyColumnNames"
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public void deleteRecords(String table, String[] keyColumnsNames, String[] keyValues) throws SQLException {
        StringBuilder sqlQuery =  new StringBuilder("DELETE FROM "+table+" WHERE");
        for(int i = 0; i < keyColumnsNames.length; i++){
            sqlQuery.append(" ").append(keyColumnsNames[i]).append(" = ").append(keyValues[i]).append(keyColumnsNames.length == i + 1 ? "" : " AND");
        }
        statement.executeUpdate(sqlQuery.toString());
        //System.out.println(sqlQuery);
    }

    /** Returns all data from table as ResultSet
     *
     * @param table name of table
     * @return all data from table as ResultSet
     * @throws SQLException table name is wrong, or something's with database
     */
    public ResultSet selectRecords(String table) throws SQLException {
        String sqlQuery = "SELECT * FROM " + table;
        return statement.executeQuery(sqlQuery);
    }

    /** Returns records of table where values of columns from "keyColumnNames" are equal to "keyValues"
     * and columns of returned result are in "columns"
     *
     * @param table table name
     * @param keyColumnsNames names of columns by which you find a record, in general - names of primary key columns
     * @param keyValues values of columns by which you find a record, same length as "keyColumnNames"
     * @param columns names of columns which are returned
     * @return data from table as ResultSet
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public ResultSet selectSpecifiedRecords(String table, String[] keyColumnsNames,
                                            String[] keyValues, String[] columns) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++){
            sqlQuery.append(columns[i]).append(columns.length==i+1?"":", ");
        }
        sqlQuery.append(" FROM ").append(table).append(" WHERE");
        for(int i = 0; i < keyColumnsNames.length; i++){
            sqlQuery.append(" ").append(keyColumnsNames[i]).append(" = ").append(keyValues[i]).append(keyColumnsNames.length == i + 1 ? "" : " AND");
        }
        // System.out.println(sqlQuery);
        return statement.executeQuery(sqlQuery.toString());
    }
}
