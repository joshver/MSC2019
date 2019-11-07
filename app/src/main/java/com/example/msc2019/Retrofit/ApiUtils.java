package com.example.msc2019.Retrofit;


public class ApiUtils {

    private ApiUtils() {
    }

    public static APIService getAPIService() {
        return com.example.msc2019.Retrofit.RetrofitClient.getClientService( URLConfig.URL.PRODUCCION.URL ).create( APIService.class );
    }
}

class URLConfig {

    public enum URL{
        PRODUCCION("http://spring-mvc-crud.jl.serv.net.mx/");
        public String URL;
        URL(String URL){
            this.URL = URL;
        }
    }
}