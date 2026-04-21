package com.evently;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * LoginDao: Validates participant login credentials against database
 */
public class LoginDao {

    public static boolean validate(String username, String password) throws SQLException {
        boolean status = false;
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Eventlydb", "root", "821108")) {

                // ✅ Use correct column names from your table
                String sql = "SELECT * FROM plogindetails WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    status = true; // Login successful
                }

                rs.close();
                ps.close();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }
}