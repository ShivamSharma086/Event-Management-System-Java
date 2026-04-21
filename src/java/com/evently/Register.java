package com.evently;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

import com.razorpay.RazorpayClient;
import com.razorpay.Order;
import org.json.JSONObject;

public class Register extends HttpServlet {

    private static final String RAZORPAY_KEY_ID = "rzp_test_SaJUDo09QpGZqM";
    private static final String RAZORPAY_KEY_SECRET = "1qkTxGYtZ3pI0XbkVqACmtvx";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String pname = request.getParameter("pname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String ename = request.getParameter("ename");
        String enumNo = request.getParameter("enum");

        if (pname.isBlank() || email.isBlank() || phone.isBlank()
                || ename.isBlank() || enumNo.isBlank()) {

            out.println("<script>");
            out.println("alert('Please Enter All Details!!!');");
            out.println("location='Registration.html';");
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

            int eventNo = Integer.parseInt(enumNo);
            int fee = 0;

            // GET EVENT FEE
            PreparedStatement pst1 = con.prepareStatement(
                    "SELECT fee FROM Event WHERE EventNo=?"
            );

            pst1.setInt(1, eventNo);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                fee = rs.getInt("fee");
            } else {
                out.println("<h3>Invalid Event Number</h3>");
                return;
            }

            // 🔥 FIX START: CHECK EXISTING REGISTRATION FIRST
            String ticketID = null;

            PreparedStatement check = con.prepareStatement(
                    "SELECT ticket_id FROM registration WHERE email=? AND event_no=?"
            );

            check.setString(1, email);
            check.setInt(2, eventNo);

            ResultSet rcheck = check.executeQuery();

            if (rcheck.next()) {
                ticketID = rcheck.getString("ticket_id");
            }

            // IF NOT EXISTS → GENERATE ONLY ONCE
            if (ticketID == null || ticketID.isEmpty()) {

                ticketID = "EVT" + System.currentTimeMillis();

                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO registration(pname,email,phone,ename,event_no,ticket_id) VALUES(?,?,?,?,?,?)"
                );

                pst.setString(1, pname);
                pst.setString(2, email);
                pst.setString(3, phone);
                pst.setString(4, ename);
                pst.setInt(5, eventNo);
                pst.setString(6, ticketID);

                pst.executeUpdate();
            }
            // 🔥 FIX END

            // CREATE RAZORPAY ORDER (UNCHANGED)
            RazorpayClient client = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", fee * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "event_" + eventNo);

            Order order = client.orders.create(orderRequest);
            String orderId = order.get("id");

            // PAYMENT PAGE (UNCHANGED)
            out.println("<html><head>");
            out.println("<script src='https://checkout.razorpay.com/v1/checkout.js'></script>");
            out.println("</head><body>");

            out.println("<script>");

            out.println("var options = {");
            out.println("\"key\": \"" + RAZORPAY_KEY_ID + "\",");
            out.println("\"amount\": \"" + (fee * 100) + "\",");
            out.println("\"currency\": \"INR\",");
            out.println("\"name\": \"Evently\",");
            out.println("\"description\": \"Event Registration\",");
            out.println("\"order_id\": \"" + orderId + "\",");

            out.println("\"handler\": function (response){");

            out.println("alert('Payment Successful');");

            out.println("window.location='ticket/qr_ticket.html?name="
                    + pname + "&event=" + ename + "&ticketid=" + ticketID + "';");

            out.println("}");
            out.println("};");

            out.println("var rzp = new Razorpay(options);");
            out.println("rzp.open();");

            out.println("</script>");

            out.println("</body></html>");

            con.close();

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}