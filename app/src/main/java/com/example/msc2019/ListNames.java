package com.example.msc2019;

public class ListNames {

    private int id;
    private String nombre;
    private int salario;

    public ListNames(int id, String nombre, int salario) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
    }

    public int getID() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getSalario() {
        return this.salario;
    }

}
