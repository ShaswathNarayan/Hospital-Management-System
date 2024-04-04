import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Utility class for establishing a database connection
public final class DbConnection {

    // Static method to get a connection to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Establishing connection to the database using JDBC
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitals", "root", "root"); // Connection
                                                                                                               // is
                                                                                                               // created
            // System.out.println("Connection Established");
        } catch (Exception e) { // Catch SQLException
            System.out.print(e);
        }
        return connection;
    }
}
