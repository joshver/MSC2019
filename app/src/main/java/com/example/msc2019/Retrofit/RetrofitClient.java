package com.example.msc2019.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {


    private static Retrofit retrofit_service = null;
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout( 60, TimeUnit.SECONDS )
//            .addInterceptor( (new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request().newBuilder().addHeader( "x-api-surespot-access-token", "..EsfdsdgWTDsadROCNS2i7HwpGHeB/R6qKfGbt496/+jNLooc1iHO4dQ==321" ).build();
//                    return chain.proceed( request );
//                }
//            }) )
            .readTimeout( 60, TimeUnit.SECONDS ).build();


    public static Retrofit getClientService(String baseUrl) {
        if (retrofit_service == null) {
            retrofit_service = new Retrofit.Builder()
                    .baseUrl( baseUrl )
                    .client( client )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .build();
        }
        return retrofit_service;
    }
}

