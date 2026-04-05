/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_impreciones.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
   private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
   private static final String JDBC_URL = "jdbc:sqlserver://CAMILOCAMACHO:1433;"
  + "databaseName=app_impresiones;"
  + "encrypt=false;"
  + "trustServerCertificate=true;";
    
    
    private static final String JDBC_USER = "sa";     
    private static final String JDBC_PASSWORD = "admi"; 
    
public static Connection getConnection() {
    try {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(
            JDBC_URL, JDBC_USER, JDBC_PASSWORD
        );
    } catch (Exception e) {
        System.err.println("❌ ERROR CONEXIÓN BD:");
        e.printStackTrace();
        return null;
    }
}
}