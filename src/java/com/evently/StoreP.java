package com.evently;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreP extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get form parameters
        String participantsName = request.getParameter("Pname");
        String userName = request.getParameter("Pusername");
        String userPassword = request.getParameter("Ppassword");
        String confirmUserPassword = request.getParameter("Cpassword");

        // Keep original variables
        String a1 = userName;
        String a2 = userPassword;
        String a3 = confirmUserPassword;
        String a4 = participantsName;

        // Validation: check if fields are blank
        if (a1.isBlank() || a2.isBlank() || a3.isBlank() || a4.isBlank()) {

            out.println("<script>alert('Please Enter Your Details!!!');</script>");

            RequestDispatcher rd = request.getRequestDispatcher("Psignup.html");
            rd.include(request, response);

            return;
        }

        // Check password match
        if (!a2.equals(a3)) {

            out.println("<script>alert('Password and Confirm Password must match!');</script>");

            RequestDispatcher rd = request.getRequestDispatcher("Psignup.html");
            rd.include(request, response);

            return;
        }

        // Database insertion
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Eventlydb",
                    "root",
                    "821108"
            );

            String mysqlQuery = "INSERT INTO plogindetails(username, password, participant_name) VALUES (?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(mysqlQuery);

            pst.setString(1, a1);
            pst.setString(2, a2);
            pst.setString(3, a4);

            pst.executeUpdate();

            // Forward to login page
            RequestDispatcher rd = request.getRequestDispatcher("Plogin.html");
            rd.forward(request, response);

            con.close();

        } catch (SQLException sqle) {

            sqle.printStackTrace();
            out.println("<h3 style='color:red'>Database Error: " + sqle.getMessage() + "</h3>");

        } catch (ClassNotFoundException cnfe) {

            cnfe.printStackTrace();
            out.println("<h3 style='color:red'>Driver Error: " + cnfe.getMessage() + "</h3>");
        }
    }
}