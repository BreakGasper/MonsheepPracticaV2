package com.example.monsheeppractica.GetterAndSetter;

public class Negocio {
    int idNegocio,idUser;
    String Nombre,TipoCompra,Calle,Numero,Interior,Codigo_Postal,
            Colonia,Municipio,Estado,Lada,Teléfono,status,TipoTelefono
            ,Id_Foto,Contra,Correo,Descripcion,servicioDomicilio;

    public Negocio(int idNegocio, int idUser, String nombre, String tipoCompra, String calle, String numero, String interior, String codigo_Postal, String colonia, String municipio, String estado, String lada, String teléfono, String status, String tipoTelefono, String id_Foto, String contra, String correo, String descripcion,String servicioDomicilio) {
        this.idNegocio = idNegocio;
        this.idUser = idUser;
        Nombre = nombre;
        TipoCompra = tipoCompra;
        Calle = calle;
        Numero = numero;
        Interior = interior;
        Codigo_Postal = codigo_Postal;
        Colonia = colonia;
        Municipio = municipio;
        Estado = estado;
        Lada = lada;
        Teléfono = teléfono;
        this.status = status;
        TipoTelefono = tipoTelefono;
        Id_Foto = id_Foto;
        Contra = contra;
        Correo = correo;
        Descripcion = descripcion;
        this.servicioDomicilio = servicioDomicilio;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipoCompra() {
        return TipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        TipoCompra = tipoCompra;
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

    public String getId_Foto() {
        return Id_Foto;
    }

    public void setId_Foto(String id_Foto) {
        Id_Foto = id_Foto;
    }

    public String getContra() {
        return Contra;
    }

    public void setContra(String contra) {
        Contra = contra;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getServicioDomicilio() {
        return servicioDomicilio;
    }

    public void setServicioDomicilio(String servicioDomicilio) {
        this.servicioDomicilio = servicioDomicilio;
    }
}
