package com.example.myapplication;

import java.io.Serializable;

public class ListElement implements Serializable {
    public String id_reporte;
    public String cod_usuario_fk ;
    public String encabezado_reporte;
    public String descripcion_reporte;
    public String ubicacion;
    public String fecha_hora_reporte;
    public String soporte_reporte;

    public ListElement(String id_reporte, String cod_usuario_fk, String encabezado_reporte, String descripcion_reporte, String ubicacion, String fecha_hora_reporte, String soporte_reporte) {
        this.id_reporte = id_reporte;
        this.cod_usuario_fk = cod_usuario_fk;
        this.encabezado_reporte = encabezado_reporte;
        this.descripcion_reporte = descripcion_reporte;
        this.ubicacion = ubicacion;
        this.fecha_hora_reporte = fecha_hora_reporte;
        this.soporte_reporte = soporte_reporte;
    }
    public String getId_reporte() {
        return id_reporte;
    }

    public void setId_reporte(String id_reporte) {
        this.id_reporte = id_reporte;
    }

    public String getCod_usuario_fk() {
        return cod_usuario_fk;
    }

    public void setCod_usuario_fk(String cod_usuario_fk) {
        this.cod_usuario_fk = cod_usuario_fk;
    }

    public String getEncabezado_reporte() {
        return encabezado_reporte;
    }

    public void setEncabezado_reporte(String encabezado_reporte) {
        this.encabezado_reporte = encabezado_reporte;
    }

    public String getDescripcion_reporte() {
        return descripcion_reporte;
    }

    public void setDescripcion_reporte(String descripcion_reporte) {
        this.descripcion_reporte = descripcion_reporte;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha_hora_reporte() {
        return fecha_hora_reporte;
    }

    public void setFecha_hora_reporte(String fecha_hora_reporte) {
        this.fecha_hora_reporte = fecha_hora_reporte;
    }

    public String getSoporte_reporte() {
        return soporte_reporte;
    }

    public void setSoporte_reporte(String soporte_reporte) {
        this.soporte_reporte = soporte_reporte;
    }
}
