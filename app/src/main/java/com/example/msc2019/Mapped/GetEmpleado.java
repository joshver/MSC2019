package com.example.msc2019.Mapped;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmpleado {

    @SerializedName("clave")
    @Expose
    private Integer clave;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("sueldo")
    @Expose
    private Integer sueldo;

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSueldo() {
        return sueldo;
    }

    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }

}