package com.mycompany.app_impreciones.controlador;

import com.mycompany.app_impreciones.modelo.Usuario;
import com.mycompany.app_impreciones.modelo.Carrito;
import com.mycompany.app_impreciones.util.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");
        List<Carrito> listaCarrito = (List<Carrito>) session.getAttribute("carritoCompras");

        if (usuario == null || listaCarrito == null || listaCarrito.isEmpty()) {
            response.sendRedirect("carrito.jsp?error=vacio");
            return;
        }

        try (Connection conn = ConexionBD.getConnection()) { // AQUÍ USAMOS TU MÉTODO EXACTO
            
            String sql = "INSERT INTO carrito_item (id_producto, id_usuario, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
               for (Carrito item : listaCarrito) {
                    ps.setInt(1, item.getProducto().getId());    // id_producto
                    ps.setInt(2, usuario.getId());               // id_usuario
                    ps.setInt(3, item.getCantidad());            // cantidad
                    ps.setBigDecimal(4, item.getProducto().getPrecio()); // precio_unitario
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            session.setAttribute("carritoCompras", null);

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<script>alert('¡Compra registrada con éxito!'); window.location='CatalogoServlet';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error en la base de datos: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}