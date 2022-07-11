package com.example.monsheeppractica.GetterAndSetter;

public class Comentario {
    int IdComentario;
    String comentario,idUser,nombreUser,idProducto,nombreProducto,urlFoto,fecha,hora;

    public Comentario(int idComentario, String comentario, String idUser, String nombreUser,
                      String idProducto, String nombreProducto, String urlFoto, String fecha, String hora) {
        IdComentario = idComentario;
        this.comentario = comentario;
        this.idUser = idUser;
        this.nombreUser = nombreUser;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.urlFoto = urlFoto;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdComentario() {
        return IdComentario;
    }

    public void setIdComentario(int idComentario) {
        IdComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
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
