package com.example.monsheeppractica.GetterAndSetter;

public class Carrito {
    int idticket;
    String idUser, Productos, cantidad, precio, idproveedor, status, idProducto, fecha, hora, cantidadDisponible;

    public Carrito(int idticket, String idUser, String productos, String cantidad, String precio, String idproveedor, String status, String idProducto, String fecha, String hora, String cantidadDisponible) {
        this.idticket = idticket;
        this.idUser = idUser;
        Productos = productos;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idproveedor = idproveedor;
        this.status = status;
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.hora = hora;
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getIdticket() {
        return idticket;
    }

    public void setIdticket(int idticket) {
        this.idticket = idticket;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getProductos() {
        return Productos;
    }

    public void setProductos(String productos) {
        Productos = productos;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(String idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(String cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}