package com.example.msc2019;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.msc2019.Mapped.GetEmpleado;
import com.example.msc2019.Retrofit.APIService;
import com.example.msc2019.Retrofit.ApiUtils;
import com.example.msc2019.Utils.Common;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtSearch;
    ListView list;
    ListViewAdapter adapter;
    private Common common = new Common();
    private ProgressDialog progressDialog;
    private APIService API = ApiUtils.getAPIService();
    ArrayList<ListNames> arraylist ;
    public static boolean starton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        list = findViewById(R.id.listview);
        txtSearch = findViewById(R.id.search);
        // Capture Text in EditText
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = txtSearch.getText().toString().toLowerCase(Locale.getDefault());
                boolean numeric = true;
                try {
                    int number = Integer.parseInt(text);
                    adapter.filterId(number);
                } catch (NumberFormatException | NullPointerException nfe) {
                    numeric = false;
                }
                try {
                    if (!numeric) {
                        adapter.filterNombre(text);
                    }
                }catch (Exception e){
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void getList(){
        progressDialog = common.new_loader( progressDialog, this, "Cargando", "Cargando" );
        arraylist =  new ArrayList<ListNames>();
           API.GetEmpleados().enqueue( new Callback<List<GetEmpleado>>() {
               @Override
               public void onResponse(Call<List<GetEmpleado>> call, Response<List<GetEmpleado>> response) {
                   if (response.isSuccessful()) {
                       for (GetEmpleado wp : response.body()) {
                           ListNames list = new ListNames(wp.getClave(), wp.getNombre(), wp.getSueldo());
                           arraylist.add(list);
                       }
                       setList();
                   } else {
                       common.hidden_loader( progressDialog );
                   }
               }
               @Override
               public void onFailure(Call<List<GetEmpleado>> call, Throwable t) {
                   Log.i( "Error", "post submitted to API." + t.getMessage().toString() );
                   common.hidden_loader( progressDialog );
                   common.Toast_Warning(MainActivity.this,"Error" );
               }
           } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (starton)
        {
            list.setAdapter(null);
            getList();
        }
        starton = false;
    }

    public void setList(){
        adapter = new ListViewAdapter(this, arraylist);
        list.setAdapter(adapter);
        common.hidden_loader( progressDialog );
    }

    public void Nuevo(View view) {
        Intent intent = new Intent(this, Agregar.class);
        this.startActivity(intent);
        starton = true;
    }
}
