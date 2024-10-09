/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package demo.curd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class EditServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name");
            String pass = request.getParameter("pass");
            String mail = request.getParameter("mail");
            String country = request.getParameter("country");
            int userId = Integer.parseInt(request.getParameter("id"));
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            String kq = "";

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=demodb", "sa", "sa");
                String updateQuery = "UPDATE users SET name=?, pass=?, mail=?, country=? WHERE id=?";
                PreparedStatement updatePs = con.prepareStatement(updateQuery);
                updatePs.setString(1, name);
                updatePs.setString(2, pass);
                updatePs.setString(3, mail);
                updatePs.setString(4, country);
                updatePs.setInt(5, userId);
                updatePs.executeUpdate();
                ps = con.prepareStatement("SELECT * FROM users");
                rs = ps.executeQuery();
                kq += "<table border=0>";
                kq += "<tr><td>Name</td><td>Password</td><td>Email</td><td>Country</td><td>Edit</td><td>Delete</td></tr>";
                while (rs.next()) {
                    kq += "<tr>";
                    kq += "<td>" + rs.getString("name") + "</td>";
                    kq += "<td>" + rs.getString("pass") + "</td>";
                    kq += "<td>" + rs.getString("mail") + "</td>";
                    kq += "<td>" + rs.getString("country") + "</td>";
                    kq += "<td><a href='EditServlet?id=" + rs.getInt("id") + "'>Edit</a></td>";
                    kq += "<td><a href='DeleteServlet?id=" + rs.getInt("id") + "'>Delete</a></td>";
                    kq += "</tr>";
                }
                kq += "</table>";
            } catch (Exception ex) {
                System.out.println("Error: " + ex.toString());
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
