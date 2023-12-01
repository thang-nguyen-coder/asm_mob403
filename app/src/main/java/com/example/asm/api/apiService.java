package com.example.asm.api;


import com.example.asm.model.DeleteSp;
import com.example.asm.model.SanPham;
import com.example.asm.model.auth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    apiService Apiservice = new Retrofit.Builder().baseUrl("http://10.0.3.2:3000/").addConverterFactory(GsonConverterFactory.create(gson)).build().create(apiService.class);


    @GET("sanpham")
    Call<List<SanPham>> getallSP();

    @POST("sanpham")
    Call<SanPham> addSP(@Body SanPham sanPham);

    @POST("xoasanpham")
    Call<DeleteSp> xoaSP(@Body DeleteSp deleteSp);

    @POST("signin")
    Call<auth> checkSignIn(@Body auth Auth);

    @POST("reg")
    Call<auth> postU(@Body auth Auth);

    @POST("upsanpham")
    Call<SanPham> updateSP(@Body SanPham sanPham);

    @POST("chitiet")
    Call<SanPham> ChitietSP(@Body SanPham sanPham);


}
