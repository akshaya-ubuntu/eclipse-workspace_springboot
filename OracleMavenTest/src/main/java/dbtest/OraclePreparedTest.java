package dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OraclePreparedTest {

    // Connection parameters pointing to your local Docker container
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
    private static final String DB_USER = "my_developer";
    private static final String DB_PASS = "SecurePass789";

    public static void main(String[] args) {
        System.out.println("====== Oracle PreparedStatement Demonstration ======");

        // 1. Establish the connection using try-with-resources
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            System.out.println("Connected successfully to FREEPDB1.");

            // ==========================================
            // CASE 1: Parameterized INSERT
            // ==========================================
            String insertSql = "INSERT INTO tasks (title, status) VALUES (?, ?)";
            
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                // Binding parameters safely by index (1-based index)
                insertStmt.setString(1, "Mastering Prepared Statements");
                insertStmt.setString(2, "IN_PROGRESS");
                
                int rowsAffected = insertStmt.executeUpdate();
                System.out.println("Successfully inserted " + rowsAffected + " row.");
            }

            // ==========================================
            // CASE 2: Parameterized SELECT (Filtering data)
            // ==========================================
            String selectSql = "SELECT task_id, title, status FROM tasks WHERE status = ?";
            
            System.out.println("\nExecuting parameterized filter query for status: 'IN_PROGRESS'...");
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                // Bind the filter criteria
                selectStmt.setString(1, "IN_PROGRESS");
                
                try (ResultSet rs = selectStmt.executeQuery()) {
                    System.out.println("--- Filtered Results ---");
                    boolean recordsFound = false;
                    
                    while (rs.next()) {
                        recordsFound = true;
                        int id = rs.getInt("task_id");
                        String title = rs.getString("title");
                        String status = rs.getString("status");
                        
                        System.out.printf("Task ID: %d | Title: %-30s | Status: %s%n", id, title, status);
                    }
                    
                    if (!recordsFound) {
                        System.out.println("No tasks found matching the criteria.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
    }
}