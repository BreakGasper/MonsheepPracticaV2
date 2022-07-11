package com.example.monsheeppractica.GetterAndSetter;

public class Seguidores {
    int idSeguir;
    String idUser,idSeguidor,nombreSeguidor,status;

    public Seguidores(int idSeguir, String idUser, String idSeguidor, String nombreSeguidor, String status) {
        this.idSeguir = idSeguir;
        this.idUser = idUser;
        this.idSeguidor = idSeguidor;
        this.nombreSeguidor = nombreSeguidor;
        this.status = status;
    }

    public int getIdSeguir() {
        return idSeguir;
    }

    public void setIdSeguir(int idSeguir) {
        this.idSeguir = idSeguir;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(String idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public String getNombreSeguidor() {
        return nombreSeguidor;
    }

    public void setNombreSeguidor(String nombreSeguidor) {
        this.nombreSeguidor = nombreSeguidor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
