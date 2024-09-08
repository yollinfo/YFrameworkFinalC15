package runners;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import ui_automation.utilities.ConfigurationReader;
import ui_automation.utilities.DBUtility;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class JDBCPractice {

    private Logger logger = LogManager.getLogger(JDBCPractice.class);
    @Test
    public void testStepByStep() {
        // * to make sure we have respective JDBC library downloaded (POM)
        String jdbcLibrary = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        // * to have the credentials
        String url = ConfigurationReader.getProperty("ui-config.properties", "mb.database.url");
        String username = ConfigurationReader.getProperty("ui-config.properties", "mb.database.username");
        String pwd = ConfigurationReader.getProperty("ui-config.properties", "mb.database.password");
        // * to have a valid sql query
        String query = "select * from Expenses where name = 'SeptemberEightTravelExpense';";



        /* 1 step in JDBC connection is to load and register the driver */
        try {
            Class.forName(jdbcLibrary);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            /* 2nd step is create a connection */
            Connection connection = DriverManager.getConnection(url, username, pwd);
            /*  3rd step is to create a Statement obj */
            Statement statement = connection.createStatement();

            /* 4th step is to execute the query and get resultSet */
            ResultSet resultSet = statement.executeQuery(query);
            // get how many column returned
            int columnCount = resultSet.getMetaData().getColumnCount();
            System.out.println(columnCount);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                System.out.println(id);
            }

            // close the connections
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void executeSqlQueryFromUtility(){
        DBUtility.openConnection();
        logger.info("Connection created");
        List<Map<String, Object>> tableData = DBUtility.executeSQLQuery("select * from Expenses where name = 'SeptemberEightTravelExpense';");
        System.out.println(tableData);

        DBUtility.closeConnection();
        logger.error("There is an error");

    }
}
