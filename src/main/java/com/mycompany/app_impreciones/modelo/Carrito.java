/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_impreciones.modelo;



import java.math.BigDecimal;

public class Carrito {
    private int item;            // Número de ítem en la lista (1, 2, 3...)
    private Producto producto;    // El objeto Producto que se está comprando
    private int cantidad;        // Cantidad de ese producto
    private BigDecimal subTotal;  // Precio total por la cantidad

    // Constructor vacío
    public Carrito() {}

    // Constructor para inicializar
    public Carrito(int item, Producto producto, int cantidad, BigDecimal subTotal) {
        this.item = item;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    // Getters y Setters
    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    /**
     * Calcula el subtotal multiplicando el precio del producto por la cantidad.
     */
    public void calcularSubTotal() {
        if (producto != null && producto.getPrecio() != null) {
            // Multiplica el precio del producto (BigDecimal) por la cantidad (int)
            this.subTotal = producto.getPrecio().multiply(BigDecimal.valueOf(this.cantidad));
        } else {
            this.subTotal = BigDecimal.ZERO;
        }
    }
}
