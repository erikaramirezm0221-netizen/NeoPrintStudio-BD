/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_impreciones.dao;

import com.mycompany.app_impreciones.modelo.Usuario;
import com.mycompany.app_impreciones.util.ConexionBD; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    
    // El código busca que las columnas se llamen 'email' y 'password'
    private static final String SQL_VALIDAR = "SELECT id, nombre, email, rol FROM dbo.usuario WHERE email = ? AND password = ?";
    private static final String SQL_BUSCAR_POR_EMAIL = "SELECT id, nombre, email, rol FROM dbo.usuario WHERE email = ?";

public Usuario validar(String email, String password) {

    System.out.println("👉 Entró a UsuarioDAO.validar()");
    System.out.println("👉 Email recibido: [" + email + "]");
    System.out.println("👉 Password recibido: [" + password + "]");

    Usuario usuario = null;
    Connection con = ConexionBD.getConnection();

    if (con == null) {
        System.err.println("❌ ERROR: conexión NULL");
        return null;
    }

    // 🔎 PASO CLAVE: verificar la base de datos activa
    try {
        PreparedStatement psDB = con.prepareStatement("SELECT DB_NAME() AS bd");
        ResultSet rsDB = psDB.executeQuery();
        if (rsDB.next()) {
            System.out.println("👉 BASE DE DATOS ACTIVA: " + rsDB.getString("bd"));
        }
        rsDB.close();
        psDB.close();
    } catch (SQLException e) {
        System.err.println("❌ Error al verificar la BD: " + e.getMessage());
    }

    // 🔐 CONSULTA REAL DE LOGIN
    try (PreparedStatement ps = con.prepareStatement(SQL_VALIDAR)) {

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("✅ USUARIO ENCONTRADO EN BD");

            usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setEmail(rs.getString("email"));

        } else {
            System.out.println("❌ NO SE ENCONTRÓ USUARIO");
        }

        rs.close();

    } catch (SQLException e) {
        System.err.println("❌ ERROR SQL LOGIN: " + e.getMessage());
    } finally {
        try { con.close(); } catch (SQLException ignored) {}
    }

    return usuario;
}
}