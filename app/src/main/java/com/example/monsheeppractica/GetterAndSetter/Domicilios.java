package com.example.monsheeppractica.GetterAndSetter;

public class Domicilios {
    int idDomicilio;
    String idUser,NombreCompleto,Domicilio,Colonia,Vecindario,Celular,CodigoPostal,numero,municipio;

    public Domicilios(int idDomicilio, String idUser, String nombreCompleto, String domicilio, String colonia, String vecindario, String celular, String codigoPostal, String numero, String municipio) {
        this.idDomicilio = idDomicilio;
        this.idUser = idUser;
        NombreCompleto = nombreCompleto;
        Domicilio = domicilio;
        Colonia = colonia;
        Vecindario = vecindario;
        Celular = celular;
        CodigoPostal = codigoPostal;
        this.numero = numero;
        this.municipio = municipio;
    }

    public int getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public void setDomicilio(String domicilio) {
        Domicilio = domicilio;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getVecindario() {
        return Vecindario;
    }

    public void setVecindario(String vecindario) {
        Vecindario = vecindario;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        CodigoPostal = codigoPostal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
