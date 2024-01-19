package bloodbankdonationsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    public static Connection connectDb() {
        String databaseURL = "jdbc:mysql://localhost:3307/bloodbank";
        String username = "root";
        String password = "";

        Connection connection = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(databaseURL, username, password);

            // Use the connection as needed (perform database operations)

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately in a real application
        }

        return connection;
    }
}
