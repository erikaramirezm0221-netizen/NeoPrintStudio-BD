package com.mycompany.app_impreciones.controlador;

import com.mycompany.app_impreciones.modelo.Usuario;
import com.mycompany.app_impreciones.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Esta es la clave: Si alguien entra por URL o enlace (GET), 
        // lo mandamos al doPost para que procese la acción.
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion != null && accion.equals("login")) {
            String email = request.getParameter("email"); 
            String password = request.getParameter("password");

            Usuario usuario = usuarioDAO.validar(email, password);

            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogeado", usuario);
                response.sendRedirect("CatalogoServlet"); 
                
            } else {
                request.setAttribute("error", "Credenciales incorrectas o la base de datos no está disponible.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } 
        else if (accion != null && accion.equals("cerrarSesion")) {
            // Lógica de cierre de sesión
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Mata la sesión
            }
            // Redirige al login después de limpiar la sesión
            response.sendRedirect("login.jsp");
        } 
        else {
            // Si no hay acción válida, regresamos al login por defecto
             response.sendRedirect("login.jsp");
        }
    }
}