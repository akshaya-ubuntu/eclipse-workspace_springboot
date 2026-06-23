package dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJdbcTest {

    // Connection Parameters pointing to your local Docker Container
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
    private static final String DB_USER = "my_developer";
    private static final String DB_PASS = "SecurePass789";

    public static void main(String[] args) {
        System.out.println("====== Connecting to Oracle Database Free ======");

        // 1. Establish Connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            System.out.println("Connection successful!\n");

            // 2. Perform an INSERT operation
            String insertSql = "INSERT INTO tasks (title, status) VALUES (?, ?)";
            try (PreparedStatement pStmt = conn.prepareStatement(insertSql)) {
                pStmt.setString(1, "Test connection via JDBC Code");
                pStmt.setString(2, "IN_PROGRESS");
                int rowsInserted = pStmt.executeUpdate();
                System.out.println("Inserted " + rowsInserted + " row successfully.");
            }

            // 3. Perform a SELECT operation
            String selectSql = "SELECT task_id, title, status, created_at FROM tasks";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSql)) {
                
                System.out.println("\n--- Current Data in TASKS Table ---");
                while (rs.next()) {
                    int id = rs.getInt("task_id");
                    String title = rs.getString("title");
                    String status = rs.getString("status");
                    String timestamp = rs.getString("created_at");
                    
                    System.out.printf("ID: %d | Title: %-30s | Status: %-12s | Created: %s%n", 
                            id, title, status, timestamp);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database connection or execution failed!");
            e.printStackTrace();
        }
    }
}