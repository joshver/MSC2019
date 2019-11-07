package com.example.msc2019;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.msc2019.Mapped.Empleados;
import com.example.msc2019.Retrofit.APIService;
import com.example.msc2019.Retrofit.ApiUtils;
import com.example.msc2019.Utils.Common;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Modificar extends AppCompatActivity {

    EditText txtNombre, txtSueldo;
    int id;
    private APIService API = ApiUtils.getAPIService();
    private Common common = new Common();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        txtNombre = findViewById(R.id.txtNombre);
        txtSueldo = findViewById(R.id.txtSueldo);

        Intent i = getIntent();
        id = i.getIntExtra("ID",0);
        txtNombre.setText(i.getStringExtra("Nombre"));
        txtSueldo.setText(i.getIntExtra("Salario",0)+"");

    }

    public void Eliminar(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        progressDialog = common.new_loader( progressDialog, Modificar.this, "Cargando", "Cargando" );
                        API.EliminarEmpleado(id).enqueue(new Callback<Empleados>() {
                            @Override
                            public void onResponse(Call<Empleados> call, Response<Empleados> response) {
                                try{
                                    int test = response.body().getStatus();
                                    common.hidden_loader( progressDialog );
                                    if (test == 1){
                                        common.Toast_Warning( Modificar.this,"Exito" );
                                        finish();
                                    }else{
                                        common.Toast_Warning( Modificar.this,"Error" );
                                    }
                                }catch (Exception e){
                                    common.hidden_loader( progressDialog );
                                    common.Toast_Warning( Modificar.this,"error" );
                                }
                            }
                            @Override
                            public void onFailure(Call<Empleados> call, Throwable t) {
                                common.hidden_loader( progressDialog );
                                common.Toast_Warning( Modificar.this,"Fail" );
                            }
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas eliminar?").
                setNegativeButton("No", dialogClickListener).setPositiveButton("Si", dialogClickListener).show();
    }

    public void Aceptar(View view) {
        if(!common.isNullOrEmpty(txtNombre.getText().toString(), txtSueldo.getText().toString()))
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("clave", id);
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
                        if (test == 1){
                            common.Toast_Warning( Modificar.this,"Exito" );
                            finish();
                        }else{
                            common.Toast_Warning( Modificar.this,"Error" );
                        }
                    }catch (Exception e){
                        common.hidden_loader( progressDialog );
                        common.Toast_Warning( Modificar.this,"error" );
                    }
                }
                @Override
                public void onFailure(Call<Empleados> call, Throwable t) {
                    common.hidden_loader( progressDialog );
                    common.Toast_Warning( Modificar.this,"Fail" );
                }
            });
        }
        else
        {
            common.Toast_Warning( this,"Asegurate de que los campos estén llenos" );
        }
    }

    public void Cancelat(View view) {
        finish();
    }
}
