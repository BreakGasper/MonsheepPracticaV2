package com.example.monsheeppractica.GetterAndSetter;

public class Categorias {

    int id_catgoria;
    String categoria, descripcion,  id_foto, status, id_producto;

    public Categorias(int id_catgoria, String categoria, String descripcion,  String id_foto, String status, String id_producto) {
        this.id_catgoria = id_catgoria;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.id_foto = id_foto;
        this.status = status;
        this.id_producto = id_producto;
    }
//38 155 244 color azul theme
    public int getId_catgoria() {
        return id_catgoria;
    }

    public void setId_catgoria(int id_catgoria) {
        this.id_catgoria = id_catgoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_foto() {
        return id_foto;
    }

    public void setId_foto(String id_foto) {
        this.id_foto = id_foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }
}
