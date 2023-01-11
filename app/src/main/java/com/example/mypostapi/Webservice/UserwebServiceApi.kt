package com.example.mypostapi.Webservice

import com.example.mypostapi.Model.DefaultResponse
import com.example.mypostapi.Model.LoginResponse
import com.example.mypostapi.Model.ProfileResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserwebServiceApi {
    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
       /* @Field("id")id:Int,*/
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("profile.php")
    fun profileuser(
        @Field("user_id") user_id: String
    ):Call<ProfileResponse>

}