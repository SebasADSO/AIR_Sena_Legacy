package com.example.myapplication;

import java.io.Serializable;

public class ListElement_User implements Serializable {
    // Se establece los objetos en publicos
    public String cod_usuario;
    public String tipo_docu_usuario;
    public String cedula_usuario;
    public String nombre_usuario;
    public String apell_usuario;
    public String email_usuario;
    public String pass_user;
    public String estado;
    public String rol_user;
    // Se establece el metodo que pasara todos los elementos hacia sus repectivos objevtos
    public ListElement_User(String cod_usuario, String tipo_docu_usuario, String cedula_usuario, String nombre_usuario, String apell_usuario, String email_usuario, String pass_user, String estado, String rol_user) {
        this.cod_usuario = cod_usuario;
        this.tipo_docu_usuario = tipo_docu_usuario;
        this.cedula_usuario = cedula_usuario;
        this.nombre_usuario = nombre_usuario;
        this.apell_usuario = apell_usuario;
        this.email_usuario = email_usuario;
        this.pass_user = pass_user;
        this.estado = estado;
        this.rol_user = rol_user;
    }
    // Los sisguientes son objetos
    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getTipo_docu_usuario() {
        return tipo_docu_usuario;
    }

    public void setTipo_docu_usuario(String tipo_docu_usuario) {
        this.tipo_docu_usuario = tipo_docu_usuario;
    }

    public String getCedula_usuario() {
        return cedula_usuario;
    }

    public void setCedula_usuario(String cedula_usuario) {
        this.cedula_usuario = cedula_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getApell_usuario() {
        return apell_usuario;
    }

    public void setApell_usuario(String apell_usuario) {
        this.apell_usuario = apell_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getPass_user() {
        return pass_user;
    }

    public void setPass_user(String pass_user) {
        this.pass_user = pass_user;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRol_user() {
        return rol_user;
    }

    public void setRol_user(String rol_user) {
        this.rol_user = rol_user;
    }
}
