package com.example.monsheeppractica.GetterAndSetter;

public class Roll {

    int idPromo ;
    String fecha,Hora ,oferta , status ,dato;

    public Roll(int idPromo, String fecha, String hora, String oferta, String status, String dato) {
        this.idPromo = idPromo;
        this.fecha = fecha;
        Hora = hora;
        this.oferta = oferta;
        this.status = status;
        this.dato = dato;
    }

    public int getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(int idPromo) {
        this.idPromo = idPromo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
