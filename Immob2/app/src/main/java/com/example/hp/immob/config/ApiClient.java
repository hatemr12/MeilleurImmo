package com.example.hp.immob.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp on 01/08/2017.
 */


public class ApiClient {

   static  Gson gson = new GsonBuilder()
            .setLenient()
            .create();




    //10.0.2.2
        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/app_android/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



   /* public static final Retrofit retrofitimg_uploads = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2/app_android/img_uploads/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();*/
}
