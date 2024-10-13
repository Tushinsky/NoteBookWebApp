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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergey
 */
@WebServlet("/addUrl")
public class RegisterItemServlet extends HttpServlet {

    private final String query = "INSERT INTO order_data(idOrder, idItem_data, order_date) value(?,?,?);";
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
            out.println("<title>Servlet RegisterItemServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterItemServlet at " + request.getContextPath() + "</h1>");
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection connection = null;
            try{
                connection = DriverManager.getConnection("jdbc:mysql:///computer_app", 
                "root", "masterkey");// устанавливаем соединение
                // создаём объект для выполнения запросов

                PreparedStatement ps = connection.prepareStatement(query);
                connection.setAutoCommit(false);
                // получаем параметры из тела запроса
                int idItem_data = Integer.parseInt(request.getParameter("idItem_data"));
                int idOrder = Integer.parseInt(request.getParameter("idOrder"));
                LocalDate now = LocalDate.now();// получаем дату заказа
                ps.setInt(1, idOrder);// задаём параметр запроса
                ps.setInt(2, idItem_data);// задаём параметр запроса
                ps.setDate(3, Date.valueOf(now));// задаём параметр запроса
                int row = ps.executeUpdate();
                if(row > 0) {
                    connection.commit();
                    connection.setAutoCommit(true);
                    out.println("<h2>Добавление прошло успешно.</h2>");
                } else {
                    out.println("<h2>Добавление не удалось.</h2>");
                }
                
            } catch(SQLException ex) {
                Logger.getLogger(RegisterItemServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<h2>Ошибка: " + ex.getMessage() + "</h2>");
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(RegisterItemServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            out.println("<br>");
            // добавляем ссылки для возврата на главную страницу и на страницу с заказами
            out.println("<a href=\"index.html\">На главную страницу</a>");
            out.println("<a href=\"orders\">Корзина заказов</a>");
            out.println("<a href=\"shop\">В магазин</a>");
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
//        processRequest(request, response);
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
