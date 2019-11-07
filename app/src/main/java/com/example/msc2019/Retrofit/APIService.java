package com.example.msc2019.Retrofit;

import com.example.msc2019.Mapped.Empleados;
import com.example.msc2019.Mapped.GetEmpleado;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    //Get
    @GET("getAll.htm")
    Call<List<GetEmpleado>> GetEmpleados();

    //Agregar/Modificar
    @GET("apiIoU.htm")
    Call<Empleados> AgegarModificiarEmpleado(@Query("data") String data);

    //Eliminar
    @GET("deleteById.htm")
    Call<Empleados> EliminarEmpleado(@Query("clave") int clave);
}
