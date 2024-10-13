/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.notebookwebapp;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergey
 */
@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet {

    private final String query = "SELECT maker_name, name, "
            + "technical_data FROM item_data WHERE id=?;";
    private final String sqlString = "SELECT name FROM item_type WHERE id=(SELECT "
            + "idItem_type FROM item_data WHERE id=?);";
//    private final String query = "INSERT INTO order_data(idOrder, idItem_data, order_date) value(?,?,?);";
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddItemServlet</title>");
            out.println("<link rel='stylesheet' href='.../java/css/style.css'");
            out.println("</head>");
            out.println("<body>");
            
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try(Connection connection = DriverManager.getConnection("jdbc:mysql:///computer_app", 
                    "root", "masterkey");// устанавливаем соединение
                    
                    ) {
                // создаём объект для выполнения запросов
                PreparedStatement ps = connection.prepareStatement(sqlString);
                
                // получаем параметры из тела запроса
                int id = Integer.parseInt(request.getParameter("id"));
                int idOrder = Integer.parseInt(request.getParameter("idOrder"));
                
                // получаем тип выбранной запчасти для ноутбука
                ps.setInt(1, id);// задаём параметр запроса
                ResultSet rs = ps.executeQuery();// получаем результат запроса
                rs.next();
                out.println("<h1>" + rs.getString(1) + "</h1>");// выводим наименование
                
                // получаем данные
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);// задаём параметр запроса
                rs = ps.executeQuery();// получаем результат запроса
                rs.next();
                out.println("<div class='main'>");
                // форма для отправки запроса в базу данных
                out.println("<form action='addUrl?idItem_data=" + id + "&idOrder=" + idOrder + "' method='post'>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Фирма</th>");
                out.println("<th>Наименование</th>");
                out.println("<th>Характеристики</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>" + rs.getString(1) + "</td>");
                out.println("<td>" + rs.getString(2) + "</td>");
                out.println("<td>" + rs.getString(3) + "</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<br><input type='submit' value='Купить' class='submit'>");
                out.println("<input type='reset' value='отменить' class='reset'>");
                out.println("</form>");
                out.println("</div>");
            } catch(SQLException ex) {
                Logger.getLogger(AddItemServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<h2>" + ex.getMessage() + "</h2>");
            }
            
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
//        processRequest(request, response);
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
