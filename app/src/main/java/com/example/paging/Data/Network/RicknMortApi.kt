package com.example.paging.Data.Network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RicknMortApi {


    @GET("character")
    suspend fun getallcharacters(@Query("page")page : Int) : List<Characters>



    companion object{
        val resulturl = "https://rickandmortyapi.com/api/"

        operator  fun invoke() : RicknMortApi{
          return  Retrofit.Builder()
              .baseUrl(resulturl)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
              .create(RicknMortApi::class.java)
        }

    }



}