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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergey
 */
@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

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
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try(Connection connection = DriverManager.getConnection("jdbc:mysql:///computer_app", 
                    "root", "masterkey");// устанавливаем соединение
                    // создаём объект для выполнения запросов
                    Statement st = connection.createStatement();
                    ) {
                ResultSet rs;// объект для получения результатов запросов
                int idOrder;
                // получаем номер последнего заказа
                rs = st.executeQuery("SELECT MAX(idOrder) FROM order_data;");
                if(rs.next()) {
                    idOrder = rs.getInt(1) + 1;
                } else {
                    idOrder = 1;
                }
                // создаём страницу с данными
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Магазин</title>");
                out.println("<link rel='stylesheet' href='.../java/css/style.css'");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Магазин комплектующих для ноутбуков</h1>");
                out.println("<div class=\"items\">\n" +
                    "<div class=\"item\">\n" +
                    "<h3>Процессоры</h3>\n" +
                    "<table>\n" +
                        "<tr>" +
                        "<th>Наименование</th><th>Тип</th><th>Тактовая частота</th>"
                        + "<th>Цена</th><th>Заказать</th>" +
                        "</tr>"
                );
                // получаем данные о процессорах
                rs = st.executeQuery("SELECT id, maker_name, name, technical_data, price "
                        + "FROM item_data WHERE idItem_type=1;");
                while(rs.next()){
                    out.println(
                        "<tr><td>" + rs.getString("maker_name") + "</td>\n"
                            + "<td>" + rs.getString("name") + "</td>\n"
                            + "<td>" + rs.getString("technical_data") + "</td>\n"
                            + "<td>" + rs.getFloat("price") + "</td>\n"
                            + "<td><a href='addItem?id=" + rs.getInt("id")
                            + "&idOrder=" + idOrder + "'>В корзину</td>" +
                        "</tr>");
                }
                out.println("</table>\n" +
                    "</div>\n" +
                    "<div class=\"item\">\n" +
                    "<h3>Видеокарты</h3>\n" +
                    "<table>\n" +
                        "<tr>" +
                        "<th>Наименование</th><th>Тип</th><th>Объём памяти</th>"
                        + "<th>Цена</th><th>Заказать</th>" +
                        "</tr>");
                // получаем данные о видеокартах
                rs = st.executeQuery("SELECT id, maker_name, name, technical_data, price "
                        + "FROM item_data WHERE idItem_type=6;");
                while(rs.next()){
                    out.println(
                        "<tr><td>" + rs.getString("maker_name") + "</td>\n"
                            + "<td>" + rs.getString("name") + "</td>\n"
                            + "<td>" + rs.getString("technical_data") + "</td>\n"
                            + "<td>" + rs.getFloat("price") + "</td>\n"
                            + "<td><a href='addItem?id=" + rs.getInt("id")
                            + "&idOrder=" + idOrder + "'>В корзину</td>\n" +
                        "</tr>\n");
                }
                out.println(
                    "</table>\n" +
                    "</div>\n" +
                    "<div class=\"item\">\n" +
                    "    <h3>Матрицы</h3>\n" +
                    "<table>\n" +
                        "<tr>" +
                        "<th>Наименование</th><th>Тип</th><th>Цена</th><th>Заказать</th>" +
                        "</tr>\n"
                );
                // получаем данные о матрицах
                rs = st.executeQuery("SELECT id, maker_name, name, technical_data, price "
                        + "FROM item_data WHERE idItem_type=3;");
                while(rs.next()){
                    out.println(
                        "<tr><td>" + rs.getString("maker_name") + "</td>\n"
                            + "<td>" + rs.getString("name") + "</td>\n"
                            + "<td>" + rs.getString("technical_data") + "</td>\n"
                            + "<td>" + rs.getFloat("price") + "</td>\n"
                            + "<td><a href='addItem?id=" + rs.getInt("id")
                            + "&idOrder=" + idOrder + "'>В корзину</td>\n" +
                        "</tr>\n");
                }
                out.println(
                    "</table>\n" +
                    "</div>\n" +
                    "<div class=\"item\">\n" +
                    "    <h3>Клавиатуры</h3>\n" +
                    "<table>\n" +
                        "<tr>" +
                        "<th>Наименование</th><th>Тип</th><td>Подсветка</td>"
                            + "<th>Цена</th><th>Заказать</th>" +
                        "</tr>\n"
                );
                // получаем данные о клавиатурах
                rs = st.executeQuery("SELECT id, maker_name, name, technical_data, price "
                        + "FROM item_data WHERE idItem_type=2;");
                String backlight;
                while(rs.next()){
                    backlight = rs.getShort("technical_data") == 0 ? "Да" : "Нет";
                    out.println(
                        "<tr><td>" + rs.getString("maker_name") + "</td>\n"
                            + "<td>" + rs.getString("name") + "</td>\n"
                            + "<td>" + backlight + "</td>\n"
                            + "<td>" + rs.getFloat("price") + "</td>\n"
                            + "<td><a href='addItem?id=" + rs.getInt("id")
                            + "&idOrder=" + idOrder + "'>В корзину</td>\n" +
                        "</tr>");
                }
                out.println(
                    "</table>\n" +
                    "</div>\n" +
                    "<div class=\"item\">\n" +
                    "    <h3>Операционная система</h3>\n" +
                    "<table>\n" +
                        "<tr>" +
                        "<th>Фирма</th><th>Наименование</th><th>Цена</th><th>Заказать</th>" +
                        "</tr>\n"
                );
                // получаем данные об операционных системах
                rs = st.executeQuery("SELECT id, maker_name, name, price "
                        + "FROM item_data WHERE idItem_type=4;");
                while(rs.next()){
                    out.println(
                        "<tr><td>" + rs.getString("maker_name") + "</td>\n"
                            + "<td>" + rs.getString("name") + "</td>\n"
                            + "<td>" + rs.getFloat("price") + "</td>\n"
                            + "<td><a href='addItem?id=" + rs.getInt("id")
                            + "&idOrder=" + idOrder + "'>В корзину</td>\n" +
                        "</tr>");
                }
                out.println(
                    "</table>\n" +
                    "</div>\n" +
                    "<div class=\"item\">\n" +
                    "    <h3>Жёсткий диск</h3>\n" +
                    "<table>\n" +
                        "<tr>" +
                        "<th>Наименование</th><th>Тип</th><td>Ёмкость</td>"
                            + "<th>Цена</th><th>Заказать</th>" +
                        "</tr>\n"
                );
                // получаем данные о жёстких дисках
                rs = st.executeQuery("SELECT id, maker_name, name, technical_data, price "
                        + "FROM item_data WHERE idItem_type=5;");
                while(rs.next()){
                    out.println(
                        "<tr><td>" + rs.getString("maker_name") + "</td>\n"
                            + "<td>" + rs.getString("name") + "</td>\n"
                            + "<td>" + rs.getString("technical_data") + "</td>\n"
                            + "<td>" + rs.getFloat("price") + "</td>\n"
                            + "<td><a href='addItem?id=" + rs.getInt("id")
                            + "&idOrder=" + idOrder + "'>В корзину</td>\n" +
                        "</tr>");
                }
                out.println(
                    "    </table>\n" +
                    "</div>\n" +
                    "</div>");
                out.println("<br>");
                // добавляем ссылки для возврата на главную страницу и на страницу с заказами
                out.println("<a href='index.html'>На главную страницу</a>");
                out.println("<a href='orders?idOrder=" + idOrder + "'>Корзина заказов</a>");
                out.println("</body>");
                out.println("</html>");
            } catch(SQLException ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<h2>" + ex.getMessage() + "</h2>");
            }
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
