package ui_automation.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

    private static Logger logger = LogManager.getLogger(DBUtility.class);
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    /**
     * This method is designed to create a connection with mealb database
     * It'll load the com.microsoft.sqlserver.jdbc.SQLServerDriver and register it
     * Then it'll create the connection object
     * @throws SQLException
     */
    public static void openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(ConfigurationReader.getProperty("ui-config.properties","mb.database.url"),
                    ConfigurationReader.getProperty("ui-config.properties","mb.database.username"),
                    ConfigurationReader.getProperty("ui-config.properties","mb.database.password"));
            logger.info("The connection is successfull");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is accepting the
     * @param className - JDBC driver class name for load and register
     * @param url - Database URL
     * @param username - Database username
     * @param pwd - Database pwd
     *              * It'll load the specified driver and register it
     *              * Then it'll create the connection object
     */
    public static void openConnection(String className, String url, String username, String pwd){
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, username, pwd);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param query is valid SQL statement
     * @return
     */
    public static List<Map<String, Object>> executeSQLQuery(String query){

        // We already have connection object
        // Create a list of the map having key as String for column names
        //                                 value as Object for cell value
        List<Map<String, Object>> table = new ArrayList<>();
        try {
            // create a Statement object
            statement = connection.createStatement();
            // execute the query and get resultSet
            resultSet = statement.executeQuery(query);
            // get column number
            ResultSetMetaData metaData = resultSet.getMetaData();
            int totalColumnCount = metaData.getColumnCount();

            // Iterate over the result set, if there's a row go over that row
            while(resultSet.next()){
                // create an individual map to hold the row information
                Map<String, Object> rowInformation = new HashMap<>();

                for(int i = 1; i <= totalColumnCount; i++){
                    // get columnName using index value
                    String cellHeader = metaData.getColumnName(i);
                    // get cell value from that row
                    Object cellValue = resultSet.getObject(i);
                    // put that info to map
                    rowInformation.put(cellHeader, cellValue);
                }
                table.add(rowInformation);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return table;
    }

    /**
     * This method has to be called after executeQuery()
     *
     */
    public static void closeConnection(){

        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }
}








