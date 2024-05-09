package com.example.myapplication;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Medicine {
    @POST("medicineallocate.php")
    Call<JsonElement> saveMedicine(@Body JsonArray jsonArray);
}

