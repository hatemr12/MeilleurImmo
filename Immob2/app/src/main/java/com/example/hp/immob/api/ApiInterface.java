package com.example.hp.immob.api;

import com.example.hp.immob.model.Bien;
import com.example.hp.immob.model.Client;
import com.example.hp.immob.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("addUser.php")
    Call<String> addUser(@Query("username") String username,@Query("nom") String nom,@Query("prenom") String prenom,@Query("tel") String tel,@Query("password") String password);

    @GET("updateUser.php")
    Call<String> updateUser(@Query("username") String username,@Query("nom") String nom,@Query("prenom") String prenom,@Query("tel") String tel,@Query("password") String password);

    @GET("login.php")
    Call<Client> login(@Query("username") String username, @Query("password") String password);

    @GET("getAnnByRegion.php")
    Call<List<Bien>> getAnnByRegion(@Query("region") String region);

    @GET("getAllGoods.php")
    Call<List<Bien>> getAllGoods();

    @GET("getImg.php")
    Call<List<Image>> getImg(@Query("id") String id);

    @GET("getAnnByUserId.php")
    Call<List<Bien>> getAnnByUserId(@Query("id") String id);

    @GET("deleteAnnById.php")
    Call<String> deleteAnnById(@Query("id") String id);

    @GET("getAnnById.php")
    Call<List<Bien>> getAnnById(@Query("id") String id);

    @GET("getUserById.php")
    Call<List<Client>> getUserById(@Query("id") String id);

    @GET("getImgById.php")
    Call<List<Image>> getImgById(@Query("id") String id);

    @GET("deleteUserById.php")
    Call<String> deleteUserById(@Query("id") String id);

    @GET("addBien.php")
    Call<String> addBien(@Query("username") String username,@Query("titre") String titre,@Query("prix") String prix,@Query("region") String region,@Query("ville") String ville,@Query("description") String description,@Query("latitude") String latitude,@Query("longitude") String longitude);

    @FormUrlEncoded
    @POST("uploadImg.php")
    Call<String> uploadImg(@Field("id_bien") String id_bien,@Field("image") String image);


}
