package com.example.msc2019.Mapped;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Empleados {

    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Empleados withStatus(Integer status) {
        this.status = status;
        return this;
    }

}