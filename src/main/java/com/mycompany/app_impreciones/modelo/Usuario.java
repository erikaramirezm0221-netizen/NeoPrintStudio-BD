package com.mycompany.app_impreciones.modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String email; 
    private String password; // Se debe manejar cifrado en producción
    // Si necesitas un campo 'rol', agrégalo aquí: private String rol; 

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(int id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    // Setters (Solo necesitamos uno para cada campo)
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
