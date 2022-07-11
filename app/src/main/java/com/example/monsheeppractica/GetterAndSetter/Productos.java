package com.example.monsheeppractica.GetterAndSetter;

public class Productos {

    int id_producto;
    String producto,costo,ventaMenudeo,ventaMayoreo,
    marca,color_p,unidadMedida,categoria_nombre,id_categoria,Id_Foto,status;
    String cantidadMinima;
    String idUser="",idFotoUser,NombreUser,IdNegocio,fecha,hora;

    public Productos(int id_producto, String producto, String costo,
                     String ventaMenudeo, String ventaMayoreo, String marca,
                     String color_p, String unidadMedida, String categoria_nombre,
                     String id_categoria, String id_Foto, String status, String cantidadMinima,
                     String idUser, String idFotoUser, String nombreUser,String IdNegocio, String fecha, String hora) {
        this.id_producto = id_producto;
        this.producto = producto;
        this.costo = costo;
        this.ventaMenudeo = ventaMenudeo;
        this.ventaMayoreo = ventaMayoreo;
        this.marca = marca;
        this.color_p = color_p;
        this.unidadMedida = unidadMedida;
        this.categoria_nombre = categoria_nombre;
        this.id_categoria = id_categoria;
        Id_Foto = id_Foto;
        this.status = status;
        this.cantidadMinima = cantidadMinima;
        this.idUser = idUser;
        this.idFotoUser = idFotoUser;
        this.NombreUser = nombreUser;
        this.IdNegocio= IdNegocio;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getVentaMenudeo() {
        return ventaMenudeo;
    }

    public void setVentaMenudeo(String ventaMenudeo) {
        this.ventaMenudeo = ventaMenudeo;
    }

    public String getVentaMayoreo() {
        return ventaMayoreo;
    }

    public void setVentaMayoreo(String ventaMayoreo) {
        this.ventaMayoreo = ventaMayoreo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor_p() {
        return color_p;
    }

    public void setColor_p(String color_p) {
        this.color_p = color_p;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getCategoria_nombre() {
        return categoria_nombre;
    }

    public void setCategoria_nombre(String categoria_nombre) {
        this.categoria_nombre = categoria_nombre;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getId_Foto() {
        return Id_Foto;
    }

    public void setId_Foto(String id_Foto) {
        Id_Foto = id_Foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(String cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdFotoUser() {
        return idFotoUser;
    }

    public void setIdFotoUser(String idFotoUser) {
        this.idFotoUser = idFotoUser;
    }

    public String getNombreUser() {
        return NombreUser;
    }

    public void setNombreUser(String nombreUser) {
        NombreUser = nombreUser;
    }

    public String getIdNegocio() {
        return IdNegocio;
    }

    public void setIdNegocio(String idNegocio) {
        IdNegocio = idNegocio;
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
}
