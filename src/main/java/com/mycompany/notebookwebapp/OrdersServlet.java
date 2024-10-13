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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergey
 */
@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

    private final String QUERY = "SELECT item_type.name, item_data.maker_name, "
            + "item_data.name, item_data.technical_data, item_data.price, "
            + "order_data.order_date FROM order_data INNER JOIN item_data ON "
            + "order_data.idItem_data=item_data.id INNER JOIN item_type ON "
            + "item_data.idItem_type=item_type.id WHERE order_data.idOrder=?";
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
            out.println("<title>Servlet OrdersServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Коризана заказов на " + LocalDate.now() + "</h1>");
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try(Connection connection = DriverManager.getConnection("jdbc:mysql:///computer_app", 
                    "root", "masterkey");// устанавливаем соединение
                    // создаём объект для выполнения запросов
                    PreparedStatement ps = connection.prepareStatement(QUERY);
                    ) {
                // получаем параметр запроса
                int idOrder = Integer.parseInt(request.getParameter("idOrder"));
                ps.setInt(1, idOrder);// задаём параметр запроса для выборки данных
                ResultSet rs;// объект для получения результатов запросов
                rs = ps.executeQuery();// получаем данные
                // выводим данные в виде таблицы
                out.println("<div class=\"item\">\n" +
                        "<table>\n" +
                        "<tr>" +
                        "<th>Тип</th><th>Фирма</th><th>Характеристика</th>"
                        + "<th>Цена</th><th>Дата заказа</th>" +
                        "</tr>");
                while(rs.next()){
                    out.println(
                        "<tr><td>" + rs.getString(1) + "</td>\n"
                            + "<td>" + rs.getString(2) + "</td>\n"
                            + "<td>" + rs.getString(3) + "</td>\n"
                            + "<td>" + rs.getString(4) + "</td>\n"
                            + "<td>" + rs.getFloat(5) + "</td>\n"
                            + "<td>" + rs.getDate(6) + "</td>\n" +
                        "</tr>");
                }
                out.println(
                    "    </table>\n" +
                    "</div>");
                out.println("<br>");
                // добавляем ссылки для возврата на главную страницу и на страницу с заказами
                out.println("<a href=\"index.html\">На главную страницу</a>");
                
                
            } catch(SQLException ex) {
                Logger.getLogger(OrdersServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<h2>Ошибка: " + ex.getMessage() + "</h2>");
                
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
