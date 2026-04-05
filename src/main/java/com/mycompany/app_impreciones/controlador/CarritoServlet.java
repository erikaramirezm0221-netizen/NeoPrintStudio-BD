/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.app_impreciones.controlador;

import com.mycompany.app_impreciones.dao.ProductoDAO;
import com.mycompany.app_impreciones.modelo.Carrito;
import com.mycompany.app_impreciones.modelo.Producto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/CarritoServlet"})
public class CarritoServlet extends HttpServlet {
    
    // El DAO para buscar productos por ID
    private final ProductoDAO productoDAO = new ProductoDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "ver"; // Acción por defecto
        }
        
        switch (accion) {
            case "agregar":
                agregarAlCarrito(request, response);
                break;
            case "eliminar":
                eliminarDelCarrito(request, response);
                break;
            case "actualizar":
                // Implementar lógica de actualizar cantidad
                break;
            case "ver":
            default:
                verCarrito(request, response);
                break;
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige la petición GET (ej: al hacer clic en "Ver Carrito") a la vista
        verCarrito(request, response);
    }

    /**
     * Maneja la lógica para agregar un producto al carrito de la sesión.
     */
    private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int idProducto;
        try {
            idProducto = Integer.parseInt(request.getParameter("idProducto"));
        } catch (NumberFormatException e) {
            // Manejar error si el ID no es válido (o dejar que el DAO devuelva null)
            response.sendRedirect("CatalogoServlet?error=idInvalido");
            return;
        }
        
        int cantidad = 1; // Por defecto, se agrega 1
        
        // 1. Obtener o crear la lista de carrito en la sesión
        List<Carrito> listaCarrito = (List<Carrito>) session.getAttribute("carritoCompras");
        if (listaCarrito == null) {
            listaCarrito = new ArrayList<>();
            session.setAttribute("carritoCompras", listaCarrito);
        }
        
        // 2. Verificar si el producto ya existe en el carrito
        for (Carrito item : listaCarrito) {
            if (item.getProducto().getId() == idProducto) {
                // Si existe, solo incrementamos la cantidad
                item.setCantidad(item.getCantidad() + cantidad);
                item.calcularSubTotal(); // Recalcula el subtotal
                response.sendRedirect("CatalogoServlet?msg=cantidadActualizada");
                return;
            }
        }
        
        // 3. Si el producto NO existe, lo buscamos en la BD y lo agregamos
        Producto producto = productoDAO.buscarProducto(idProducto); // Método en ProductoDAO
        
        if (producto != null) {
            // El item se usa para fines de visualización, lo establecemos como el tamaño actual + 1
            int itemActual = listaCarrito.size() + 1; 
            
            Carrito nuevoItem = new Carrito();
            nuevoItem.setItem(itemActual);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.calcularSubTotal(); // Calcula el subtotal inicial (precio * 1)
            
            listaCarrito.add(nuevoItem);
        } else {
            // Opcional: Manejar si el producto no existe en la BD
        }
        
        // Redirigir de nuevo al catálogo para que el usuario siga comprando
        response.sendRedirect("CatalogoServlet?msg=productoAgregado"); 
    }
    
    /**
     * Redirige al usuario al carrito.jsp.
     */
    private void verCarrito(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Simplemente reenvía al JSP para mostrar el contenido de la sesión
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }

    /**
     * Maneja la eliminación de un ítem del carrito por su índice (posición).
     */
    private void eliminarDelCarrito(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        // El idItem en el JSP es el loop.index, que es el índice basado en 0
        String idItemParam = request.getParameter("idItem"); 
        
        if (idItemParam != null) {
            try {
                int idItemAEliminar = Integer.parseInt(idItemParam);
                
                List<Carrito> listaCarrito = (List<Carrito>) session.getAttribute("carritoCompras");
                
                if (listaCarrito != null && idItemAEliminar >= 0 && idItemAEliminar < listaCarrito.size()) {
                    // Eliminar por índice de la lista:
                    listaCarrito.remove(idItemAEliminar);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear el índice del ítem a eliminar.");
            }
        }
        
        // Vuelve a cargar el carrito para mostrar la lista actualizada
        response.sendRedirect("CarritoServlet"); 
    }
}