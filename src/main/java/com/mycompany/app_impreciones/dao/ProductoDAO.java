/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_impreciones.dao; 

import com.mycompany.app_impreciones.util.ConexionBD;
import com.mycompany.app_impreciones.modelo.Producto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// NOTA: Si usas una interfaz (IProductoDAO), deberías agregar: implements IProductoDAO
public class ProductoDAO {
    
    // --- CONSTANTES SQL ---
    private static final String SQL_SELECT_ALL = "SELECT id, nombre, descripcion, precio, imagen_url, stock FROM producto";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nombre, descripcion, precio, imagen_url, stock FROM producto WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO producto (nombre, descripcion, precio, imagen_url, stock) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, imagen_url = ?, stock = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?";
    
    // --- CONSTRUCTOR (Opcional, pero incluido para completitud) ---
    public ProductoDAO() {
        // Inicialización, si es necesaria.
    }

    /**
     * Lista todos los productos disponibles en la base de datos para el catálogo.
     * @return Una lista de objetos Producto.
     */
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getBigDecimal("precio")); 
                producto.setImagenUrl(rs.getString("imagen_url"));
                producto.setStock(rs.getInt("stock")); 
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudieron listar los productos.");
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * Busca y retorna un objeto Producto basado en su ID.
     * Es utilizado por CarritoServlet para obtener detalles al agregar un ítem.
     * @param id El ID del producto a buscar.
     * @return El objeto Producto si se encuentra, o null si no existe.
     */
    public Producto buscarProducto(int id) {
        Producto producto = null;

        try (Connection con = ConexionBD.getConnection(); 
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setInt(1, id); 
            
            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setPrecio(rs.getBigDecimal("precio"));
                    producto.setImagenUrl(rs.getString("imagen_url"));
                    producto.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR: Al buscar producto con ID " + id);
            e.printStackTrace();
        }
        return producto;
    }

    // --- MÉTODOS CRUD ADICIONALES (Para completitud) ---

    /**
     * Inserta un nuevo producto en la base de datos.
     */
    public boolean insertarProducto(Producto producto) {
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setBigDecimal(3, producto.getPrecio());
            ps.setString(4, producto.getImagenUrl());
            ps.setInt(5, producto.getStock());

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo insertar el producto.");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza la información de un producto existente.
     */
    public boolean actualizarProducto(Producto producto) {
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setBigDecimal(3, producto.getPrecio());
            ps.setString(4, producto.getImagenUrl());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getId()); // ID en la cláusula WHERE

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo actualizar el producto.");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina un producto por su ID.
     */
    public boolean eliminarProducto(int id) {
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo eliminar el producto.");
            e.printStackTrace();
            return false;
        }
    }
}