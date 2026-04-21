package com.evently;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class TraH extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Transactions Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"total.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");

        out.println("<body>");

        out.println("<h1 style=\"text-align: center\">Welcome To Evently ... An Event Management Portal!</h1>");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Eventlydb",
                    "root",
                    "821108"
            );

            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");

            out.println("<center><h1>Transaction Details</h1></center>");

            out.println("<div>");
            out.println("<p><a href=\"TransactionDetails.html\"><button>Event Details Page</button></a></p>");

            out.println("<center>");
            out.println("<table border='1' width='50%' height='50%'>");

            out.println("<tr>");
            out.println("<th>Payment ID</th>");
            out.println("<th>Status</th>");
            out.println("<th>Amount</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            boolean hasData = false;

            while (resultSet.next()) {

                hasData = true;

                String pid = resultSet.getString("payment_id");
                String status = resultSet.getString("payment_status");
                String amount = resultSet.getString("amount");
                String date = resultSet.getString("date_time");

                out.println("<tr>");
                out.println("<td>" + pid + "</td>");
                out.println("<td>" + status + "</td>");
                out.println("<td>" + amount + "</td>");
                out.println("<td>" + date + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr>");
                out.println("<td colspan='4' style='text-align:center'>No transactions yet</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</center>");
            out.println("</div>");

            resultSet.close();
            statement.close();
            con.close();

            out.println("<div>");
            out.println("<label class=\"topnav-right\">Making every event memorable!</label>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}