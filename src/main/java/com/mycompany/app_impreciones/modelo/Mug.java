/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_impreciones.modelo;



// Mug extiende de Producto para heredar nombre, precio, etc.
public class Mug extends Producto {
    private String material; // Cerámica, metálico
    private String capacidadOz; // Onzas

    // Constructor vacío
    public Mug() {
        super();
    }
    
    // Getters y Setters
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCapacidadOz() {
        return capacidadOz;
    }

    public void setCapacidadOz(String capacidadOz) {
        this.capacidadOz = capacidadOz;
    }
}