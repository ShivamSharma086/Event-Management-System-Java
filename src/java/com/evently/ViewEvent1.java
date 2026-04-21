package com.evently;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewEvent1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Event Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"total.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            String conURL = "jdbc:mysql://localhost:3306/Eventlydb";
            String dbusername = "root";
            String dbuserpassword = "821108";

            Connection con = DriverManager.getConnection(conURL , dbusername, dbuserpassword);

            con.setAutoCommit(false);

            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from Event");

            out.println("<h1 style=\"text-align: center\">Welcome To Evently ... An Event Management Portal!</h1>");
            out.println("<center><h1>Event Details</h1></center>");
            out.println("<div>");
            out.println("<center>");

            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Event Number</th><th>Event Name</th><th>Coordinator</th><th>Coordinator Contact</th><th>Fees</th><th>Venue</th><th>Date</th></tr>");

            while(resultSet.next()){

                String n = resultSet.getString("EventNo");
                String nm = resultSet.getString("EventName");
                String co = resultSet.getString("coordinatorName");
                String cono = resultSet.getString("CoordinatorNo");
                String f = resultSet.getString("fee");
                String v = resultSet.getString("venue");
                String d = resultSet.getString("date");

                out.println("<tr><td>" + n + "</td><td>" + nm +"</td><td>"+co+"</td><td>"+cono+"</td><td>"+f+"</td><td>"+v+"</td><td>"+d+"</td></tr>");
            }

            con.commit();
            con.close();

            out.println("</table>");
            out.println("<br>");
            out.println("</div>");
            out.println("</center>");

            out.println("<div>");
            out.println("<label class=\"topnav-right\"> © 1999-2022 Evently. All rights reserved. </label>");
            out.println("</div>");

            out.print("</body>");
            out.print("</html>");

        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Exception Caught: " + e);
        }
    }
}