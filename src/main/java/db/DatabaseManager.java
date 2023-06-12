package db;
import java.sql.*;

public class DatabaseManager {
    Connection connection;
    Statement statement;
    private static DatabaseManager databaseManager;
    public static DatabaseManager getDatabaseManager() throws SQLException {
        if(databaseManager==null) databaseManager = new DatabaseManager("jdbc:ucanaccess://src/main/resources/Zlagoda.accdb;COLUMNORDER=DISPLAY");
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
    }

    /** Updates records specified by keys
     *
     * @param table name of table
     * @param keyColumnsNames names of columns by which you find a record, in general - names of primary key columns
     * @param keyValues values of columns by which you find a record, same length as "keyColumnNames"
     *                  !Note! If it is column of Strings or Date, write value as: 'value'
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
    }

    /** Deletes records specified by keys
     *
     * @param table name of table
     * @param keyColumnsNames names of columns by which you find a record, in general - names of primary key columns
     * @param keyValues values of columns by which you find a record, same length as "keyColumnNames"
     *                  !Note! If it is column of Strings or Date, write value as: 'value'
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public void deleteRecords(String table, String[] keyColumnsNames, String[] keyValues) throws SQLException {
        StringBuilder sqlQuery =  new StringBuilder("DELETE FROM "+table+" WHERE");
        for(int i = 0; i < keyColumnsNames.length; i++){
            sqlQuery.append(" ").append(keyColumnsNames[i]).append(" = ").append(keyValues[i]).append(keyColumnsNames.length == i + 1 ? "" : " AND");
        }
        statement.executeUpdate(sqlQuery.toString());
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

    /** Returns records of table where values of columns from "keyColumnNames" are equal to "findByValueCellValues"
     * and columns of returned result are in "columns"
     *
     * @param table table name
     * @param findByValueColumnNames names of columns by which you find a record, in general - names of primary key columns.
     *                        Is used as filter. For example, if you want to find records with cell's values which equal "15".
     *                        Leave empty if you don't need it
     * @param findByValueCellValues values of columns by which you find a record, same length as "findByValueColumnNames"
     *                              Leave empty if you don't need it
     *                              !Note! If it is column of Strings or Date, write value as: 'value'
     * @param findBySubstringColumnNames names of columns of String in which you want to specify substrings.
     *                                  Leave empty if you don't need it
     * @param findBySubstringCellValues substrings values, same length as "findBySubstringColumnNames"
     *                                  Leave empty if you don't need it
     * @param findWhereDateIsWithinColumnNames names of columns of DateTime in which you want to specify date.
     *                                         Use it if you need to set date between two values.
     *                                         Leave empty if you don't need it
     * @param findWhereDateIsWithinCellValues values, DateTime between which you need to get.
     *                                        Twice the length of "findWhereDateIsWithinColumnNames".
     *                                        For example, you have 2 columns where you want to specify DateTime.
     *                                        Therefore, in "findWhereDateIsWithinColumnNames" you write their names.
     *                                        Here you put 4 values: 2 which limit 1-st, 2 which limit 2-nd.
     *                                        Leave empty if you don't need it
     * @param columns names of columns which are returned
     *                Leave empty to get all columns
     * @return data from table as ResultSet
     * @throws SQLException table name or values are wrong, or something's with database
     */
    public ResultSet selectSpecifiedRecords(String table,
                                            String[] findByValueColumnNames,
                                            String[] findByValueCellValues,
                                            String[] findBySubstringColumnNames,
                                            String[] findBySubstringCellValues,
                                            String[] findWhereDateIsWithinColumnNames,
                                            Date[] findWhereDateIsWithinCellValues,
                                            String[] columns) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder("SELECT ");
        if(columns.length==0) sqlQuery.append("*");
        for (int i = 0; i < columns.length; i++){
            sqlQuery.append(columns[i]).append(columns.length==i+1?"":", ");
        }
        sqlQuery.append(" FROM ").append(table);
        if(findByValueColumnNames.length+findBySubstringColumnNames.length+findWhereDateIsWithinColumnNames.length!=0) sqlQuery.append(" WHERE ");
        for(int i = 0; i < findByValueColumnNames.length; i++){
            sqlQuery.append(findByValueColumnNames[i])
                    .append(" = ")
                    .append(findByValueCellValues[i])
                    .append(findByValueColumnNames.length == i + 1 ? "" : " AND ");
        }
        if(findByValueColumnNames.length>0&&
                (findBySubstringColumnNames.length>0||findWhereDateIsWithinColumnNames.length>0)) sqlQuery.append(" AND ");
        for(int i = 0; i < findBySubstringColumnNames.length; i++){
            sqlQuery.append(findBySubstringColumnNames[i])
                    .append(" LIKE '%")
                    .append(findBySubstringCellValues[i])
                    .append("%'")
                    .append(findBySubstringColumnNames.length == i + 1 ? "" : " AND ");
        }
        if(findBySubstringColumnNames.length>0&&findWhereDateIsWithinColumnNames.length>0) sqlQuery.append(" AND ");
        for(int i = 0; i < findWhereDateIsWithinColumnNames.length; i++){
            sqlQuery.append(findWhereDateIsWithinColumnNames[i])
                    .append(" >= '")
                    .append(findWhereDateIsWithinCellValues[i*2])
                    .append("' AND ")
                    .append(findWhereDateIsWithinColumnNames[i])
                    .append(" <= '")
                    .append(findWhereDateIsWithinCellValues[i*2+1])
                    .append("'")
                    .append(findWhereDateIsWithinColumnNames.length == i + 1 ? "" : " AND ");;
        }
        System.out.println(sqlQuery);
        return statement.executeQuery(sqlQuery.toString());
    }
}
//As example:
/*
public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = DatabaseManager.getDatabaseManager();
            String table = "Employee";
            Date date2 = new Date(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            Date date1 = new Date(date2.getTime() - Long.parseLong("1100034143412"));
            ResultSet resultSet = databaseManager.selectSpecifiedRecords(table,
                    new String[]{"id_employee", "empl_surname"}, new String[]{"'A2L8VPQNO5'", "''"},
                    new String[]{"id_employee", "empl_surname"}, new String[]{"10", "а"},
                    new String[]{"date_of_birth", "date_of_birth"}, new Date[]{date1, date2, date1, date2},
                    new String[]{});
            while (resultSet.next()) System.out.println(resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 */