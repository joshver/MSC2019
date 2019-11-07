package com.example.msc2019;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.msc2019.Mapped.Empleados;
import com.example.msc2019.Retrofit.APIService;
import com.example.msc2019.Retrofit.ApiUtils;
import com.example.msc2019.Utils.Common;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Agregar extends AppCompatActivity {

    private EditText txtNombre, txtSueldo;
    private APIService API = ApiUtils.getAPIService();
    private Common common = new Common();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        getSupportActionBar().hide();
        txtNombre = findViewById(R.id.txtNombre);
        txtSueldo = findViewById(R.id.txtSueldo);
    }

    public void Aceptar(View view) {

        if(!common.isNullOrEmpty(txtNombre.getText().toString(), txtSueldo.getText().toString()))
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("clave", 0);
            jsonObject.addProperty("nombre", txtNombre.getText().toString());
            jsonObject.addProperty("sueldo", txtSueldo.getText().toString());

            String test = jsonObject.toString();
            progressDialog = common.new_loader( progressDialog, this, "Cargando", "Cargando" );
            API.AgegarModificiarEmpleado(test).enqueue(new Callback<Empleados>() {
                @Override
                public void onResponse(Call<Empleados> call, Response<Empleados> response) {
                    try{
                       int test = response.body().getStatus();
                        common.hidden_loader( progressDialog );
                       if (test > 0){
                           common.Toast_Warning( Agregar.this,"Exito" );
                           finish();
                       }else{
                           common.Toast_Warning( Agregar.this,"Error" );
                       }
                    }catch (Exception e){
                        common.hidden_loader( progressDialog );
                        common.Toast_Warning( Agregar.this,"error" );
                    }
                }
                @Override
                public void onFailure(Call<Empleados> call, Throwable t) {
                    common.hidden_loader( progressDialog );
                    common.Toast_Warning( Agregar.this,"Fail" );
                }
            });
        }
        else
        {
            common.Toast_Warning( this,"Asegurate de que los campos estén llenos" );
        }
    }

    public void Cancelar(View view) {
        finish();
    }
}
