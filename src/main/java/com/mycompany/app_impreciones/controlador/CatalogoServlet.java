/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.app_impreciones; // Ajusta el paquete

import com.mycompany.app_impreciones.dao.ProductoDAO;
import com.mycompany.app_impreciones.modelo.Producto;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CatalogoServlet", urlPatterns = {"/CatalogoServlet"})
public class CatalogoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.productoDAO = new ProductoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la lista de productos de la BD
        List<Producto> productos = productoDAO.listarProductos();
        
        // 2. Colocar la lista en el objeto Request
        request.setAttribute("productos", productos);
        
        // 3. Redirigir a la vista JSP
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }
}