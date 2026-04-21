package com.evently;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Date;

public class AddEvent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get form values
        String eventName = request.getParameter("EventName");
        String coordinatorName = request.getParameter("coordinatorName");
        String coordinatorNo = request.getParameter("CoordinatorNo");
        String feeStr = request.getParameter("fee");
        String venue = request.getParameter("venue");
        String dateStr = request.getParameter("date");
        String upiId = request.getParameter("upi");

        // Validation
        if (eventName == null || eventName.isBlank() ||
            coordinatorName == null || coordinatorName.isBlank() ||
            coordinatorNo == null || coordinatorNo.isBlank() ||
            feeStr == null || feeStr.isBlank() ||
            venue == null || venue.isBlank() ||
            dateStr == null || dateStr.isBlank() ||
            upiId == null || upiId.isBlank()) {

            out.println("<script>");
            out.println("alert('Please Enter All Event Details including UPI ID!');");
            out.println("window.location='CreateE.html';");
            out.println("</script>");
            return;
        }

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Eventlydb",
                    "root",
                    "821108"
            );

            String query = "INSERT INTO Event (EventName, coordinatorName, CoordinatorNo, fee, venue, date, upi_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, eventName);
            pst.setString(2, coordinatorName);
            pst.setString(3, coordinatorNo);
            pst.setInt(4, Integer.parseInt(feeStr));
            pst.setString(5, venue);
            pst.setDate(6, Date.valueOf(dateStr));
            pst.setString(7, upiId);

            pst.executeUpdate();

            pst.close();
            con.close();

            // Success message
            out.println("<script>");
            out.println("alert('Success! Event Details Added Successfully!');");
            out.println("window.location='CreateE.html';");
            out.println("</script>");

        } catch (Exception e) {

            e.printStackTrace();

            String errorMsg = e.getMessage().replace("'", "");

            out.println("<script>");
            out.println("alert(\"Database Error: " + errorMsg + "\");");
            out.println("window.location='CreateE.html';");
            out.println("</script>");
        }
    }
}