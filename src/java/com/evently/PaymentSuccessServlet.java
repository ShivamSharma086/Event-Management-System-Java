package com.evently;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.sql.*;

public class PaymentSuccessServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        System.out.println("Servlet HIT");

        String payment_id = request.getParameter("payment_id");
        String payment_status = request.getParameter("payment_status");
        String amount = request.getParameter("amount");

        System.out.println("payment_id = " + payment_id);
        System.out.println("status = " + payment_status);
        System.out.println("amount = " + amount);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 🔥 CHANGE THIS (VERY IMPORTANT)
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Eventlydb",   // <-- your DB name
                "root",
                "821108"                         // <-- your real password
            );

            String sql = "INSERT INTO transactions(payment_id, payment_status, amount) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, payment_id);
            ps.setString(2, payment_status);
            ps.setString(3, amount);

            int i = ps.executeUpdate();

            if (i > 0) {
                System.out.println("DB INSERT SUCCESS");
                out.print("SUCCESS");
            } else {
                System.out.println("DB INSERT FAILED");
                out.print("FAILED");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR: " + e.getMessage());
        }
    }
}