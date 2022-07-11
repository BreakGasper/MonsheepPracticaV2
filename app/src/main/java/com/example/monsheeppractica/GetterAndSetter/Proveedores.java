package com.example.monsheeppractica.GetterAndSetter;

public class Proveedores {
    int id_proveedor;
    String Nombre,Calle,Numero,Interior,Codigo_Postal,Colonia,Municipio,Estado,Contacto,Lada,Teléfono,status,TipoTelefono;

    public Proveedores(int id_proveedor, String nombre, String calle, String numero, String interior, String codigo_Postal, String colonia, String municipio, String estado, String contacto, String lada, String teléfono, String status, String tipoTelefono) {
        this.id_proveedor = id_proveedor;
        Nombre = nombre;
        Calle = calle;
        Numero = numero;
        Interior = interior;
        Codigo_Postal = codigo_Postal;
        Colonia = colonia;
        Municipio = municipio;
        Estado = estado;
        Contacto = contacto;
        Lada = lada;
        Teléfono = teléfono;
        this.status = status;
        TipoTelefono = tipoTelefono;

    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getInterior() {
        return Interior;
    }

    public void setInterior(String interior) {
        Interior = interior;
    }

    public String getCodigo_Postal() {
        return Codigo_Postal;
    }

    public void setCodigo_Postal(String codigo_Postal) {
        Codigo_Postal = codigo_Postal;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public String getLada() {
        return Lada;
    }

    public void setLada(String lada) {
        Lada = lada;
    }

    public String getTeléfono() {
        return Teléfono;
    }

    public void setTeléfono(String teléfono) {
        Teléfono = teléfono;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoTelefono() {
        return TipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
        TipoTelefono = tipoTelefono;

    }


}
