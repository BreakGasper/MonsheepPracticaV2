package com.example.monsheeppractica.GetterAndSetter;

public class Clientes {

    int id_cliente;
    String Nombre,ApellidoPaterno,ApellidoMaterno,TipoCompra,Calle,Numero,Interior,Codigo_Postal,Colonia,
            Municipio,Estado,Lada,Teléfono,status,TipoTelefono,Alias,Id_Foto,Contra,idNegocio, correo;

    public Clientes(int id_cliente, String nombre, String apellidoPaterno, String apellidoMaterno, String tipoCompra, String calle, String numero, String interior, String codigo_Postal, String colonia, String municipio, String estado, String lada, String teléfono, String status, String tipoTelefono, String alias, String id_Foto, String contra, String idNegocio, String correo) {
        this.id_cliente = id_cliente;
        Nombre = nombre;
        ApellidoPaterno = apellidoPaterno;
        ApellidoMaterno = apellidoMaterno;
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
        Alias = alias;
        Id_Foto = id_Foto;
        Contra = contra;
        this.idNegocio = idNegocio;
        this.correo = correo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        ApellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        ApellidoMaterno = apellidoMaterno;
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

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
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

    public String getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(String idNegocio) {
        this.idNegocio = idNegocio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
