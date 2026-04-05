/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_impreciones.modelo;



// Gorras extiende de Producto para heredar nombre, precio, etc.
public class Gorra extends Producto {
    private String talla;
    private String color;

    // Constructor vacío
    public Gorra() {
        super();
    }

    // Puedes agregar constructores específicos si los necesitas

    // Getters y Setters
    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}