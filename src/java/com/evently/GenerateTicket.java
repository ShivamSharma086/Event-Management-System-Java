package com.evently;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.net.URLEncoder;

public class GenerateTicket extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");

        String name = "";
        String event = "";
        String ticketId = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Eventlydb", "root", "821108");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT participant_name, ename, ticket_id FROM registration WHERE username=?"
            );

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("participant_name");
                event = rs.getString("ename");
                ticketId = rs.getString("ticket_id");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h3 style='color:red'>DB Error: " + e.getMessage() + "</h3>");
            return;
        }

        // ❌ Ticket not generated yet
        if (ticketId == null || ticketId.isEmpty()) {
            out.println("<h3 style='color:red'>Ticket not generated yet. Please complete payment.</h3>");
            return;
        }

        // ✅ FIXED QR CONTENT (STABLE)
        String qrData = ticketId;

        String qrURL = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data="
                + URLEncoder.encode(qrData, "UTF-8");

        out.println("<html>");
        out.println("<head><title>Event Ticket</title></head>");
        out.println("<body style='text-align:center;font-family:Arial;'>");

        out.println("<h2>🎫 Evently Ticket</h2>");

        out.println("<p><b>Name:</b> " + name + "</p>");
        out.println("<p><b>Event:</b> " + event + "</p>");
        out.println("<p><b>Ticket ID:</b> " + ticketId + "</p>");

        out.println("<br><img src='" + qrURL + "'>");

        out.println("<p><b>Show this QR at entry</b></p>");

        out.println("</body></html>");
    }
}