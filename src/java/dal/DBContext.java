package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {
   public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        public static String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=BOOK_STORE10;encrypt=true;trustServerCertificate=true;";
    public static String userDB = "sa";
    public static String passDB = "123";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(dbURL, userDB, passDB);
            return con;
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            if (con != null) {
                System.out.println("Connect to Sp25_DemoPRJ Success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
